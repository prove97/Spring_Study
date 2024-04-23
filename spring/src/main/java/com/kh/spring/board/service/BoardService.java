package com.kh.spring.board.service;

import java.util.ArrayList;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

public interface BoardService {
	//게시글 총 개수 가져옴
	int selectListCount();
	
	//게시글 리스트 조회
	ArrayList<Board> selectList(PageInfo pi);
	
	//게시글 조회수 증가
	int increaseCount(int bno);
	
	//게시글 상세 조회
	Board selectBoard(int bno);
	
	//댓글 리스트 조회
	ArrayList<Reply> selectReply(int bno);
	
	//게시글 추가(insert)
	int insertBoard(Board b);
	
	//게시글 수정(update)
	int updateBoard(Board b);
}
