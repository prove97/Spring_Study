package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.board.service.BoardService;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
		
	@RequestMapping("list.bo")
	public String selectListCount(@RequestParam(value="cpage", defaultValue="1") int currentPage, Model model) {
		int boardCount = boardService.selectListCount();
//		log.info("list.bo실행");
		
		PageInfo pi = Pagination.getPageInfo(boardCount, currentPage, 10, 5);
		
		ArrayList<Board> list = boardService.selectList(pi);
		
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		
		return "board/boardListView";
		
	}

	@RequestMapping("detail.bo")
	public String selectBoard(int bno, Model model) {
		int result = boardService.increaseCount(bno);
		if(result > 0) { //조회수 증가 성공
			Board b = boardService.selectBoard(bno);
			model.addAttribute("b", b);
			return "board/boardDetailView";
		} else { //조회수 증가 실패
			model.addAttribute("errorMsg", "게시글 조회 실패");
			return "common/errorPage";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "rList.bo", produces="application/json; charset-UTF-8")
	public String ajaxSelectReplyList(int bno) {
		ArrayList<Reply> list = boardService.selectReply(bno);
		return new Gson().toJson(list);
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}

	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		//전달된 파일이 있을 경우 => 파일 이름 변경 => 서버에 저장 => 원본명, 서버업로드된 경로를 b객체에 담기
		if(!upfile.getOriginalFilename().equals("")) {
			String changeName = saveFile(upfile, session);
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		int result = boardService.insertBoard(b);
		
		if(result > 0) { //성공 => list페이지로 이동
			model.addAttribute("alert", "게시글 작성 성공");
			return "redirect:list.bo";
		} else { //실패 => 에러페이지
			model.addAttribute("errorMsg", "게시글 작성 실패");
			return "common/errorPage";
		}
		
	}
	
	//실제 넘어온 파일의 이름을 변경해서 서버에 저장하는 메소드(이 메소드는 common.Template에 작성해야함, 보기편하려고 여기 작성)
	public String saveFile(MultipartFile upfile, HttpSession session) {
		//파일명 수정 후 서버에 업로드하기("imgFile.jpg => 202404231004305488.jpg)
		String originName = upfile.getOriginalFilename();
		
		//년월일시분초
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//5자리 랜덤값
		int random = (int)(Math.random()*90000) + 10000;;
		
		//확장자 
		String ext = originName.substring(originName.lastIndexOf("."));
		
		//수정된 첨부파일 명
		String changeName = currentTime + random + ext;
		
		//첨부파일을 저장할 폴더의 물리적 경로(session)
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");  //getServletContext() : 지금 사용하고 있는 서블렛의 context가 어디인지
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {
		model.addAttribute("b", boardService.selectBoard(bno));
		return "board/boardUpdateForm";
	}
	
	@RequestMapping("update.bo") //매개변수 b 의 앞에 @ModelAttribute 생략 되어있음
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {
		//새로운 첨부파일이 넘어온 경우
		if(reupfile.getOriginalFilename().equals("")){
			//기존의 첨부파일이 있다면 => 기존 파일을 삭제
			if(b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			
			//새로 넘어온 첨부파일을 서버에 업로드 시키기
			String changeName = saveFile(reupfile, session);
			
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		/*
		 * b에 boardTitle, boardContent
		 * 
		 * 1. 새로운 첨부파일X, 기본첨부파일X
		 *    => originName : null, changeName : null
		 *    
		 * 2. 새로운 첨부파일 X, 기존첨부파일O
		 *    => originName : 기존첨부파일 이름, changeName : 기존첨부파일 경로
		 *    
		 * 3. 새로운 첨부파일O, 기존첨부파일O
		 *    => originName : 새로운 첨부파일 이름, changeName : 새로운 첨부파일 경로
		 *    
		 * 3. 새로운 첨부파일 O, 기존첨부파일X
		 *    => originName : 새로운 처부파일, changeName : 새로운 첨부파일 경로
		 */
		
		
		int result = boardService.updateBoard(b);
		
		if(result > 0) { //성공
			session.setAttribute("alertMsg", "게시글 수정 성공");
			return "redirect:detail.bo?bno="+b.getBoardNo();
		} else { //실패
			model.addAttribute("errorMsg", "게시글 수정 실패");
			return "common/errorPage";
		}
	}
	
	@ResponseBody
	@RequestMapping("rinsert.bo")
	public String ajaxinsertReply(Reply r) {
		
		//성공했을 때 success, 실패했을 때 fail
		return boardService.insertReply(r) > 0 ? "success" : "fail";
		
	}

	@ResponseBody
	@RequestMapping(value = "topList.bo", produces="application/json; charset=UTF-8")
	public String ajaxTopBoardList() {
		return new Gson().toJson(boardService.selectTopBoardList());
	}
}
