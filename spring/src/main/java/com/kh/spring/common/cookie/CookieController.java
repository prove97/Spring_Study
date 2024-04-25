package com.kh.spring.common.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CookieController {
	/*
	 * cookie
	 * 브라우저에 저장되는 데이터조각, 주로 사요자를 식별하고 상태정보를 기억하는데 사용된다.
	 * 쿠키는 클라이언트(브라우저)의 로컬 저장소에 저장이 된다.
	 * 저장된 쿠키정보는 서버에 http요청 시 헤더에 담겨 함께 전송이 된다.
	 * 
	 * 쿠키는 보안성이 낮고 개인정보 유출에 취약할 수 있다. 따라서 중요정보를 저장하는데 사용하려면 보안적인 조취가 필요하다.
	 */
	
	@RequestMapping("create")
	public String create(HttpServletResponse response) {
		//쿠키는 객체를 생성한 다음 응답정보에 첨부할 수 있다.
		//name, value속성을 필수로 작성해야 함
		
		
		Cookie ck1 = new Cookie("test1", "Lee");
		ck1.setMaxAge(60); //초단위 기준
		response.addCookie(ck1);
		
		Cookie ck2 = new Cookie("test2", "Kim");
		response.addCookie(ck2);
		
		Cookie ck3 = new Cookie("test3", "Park");
		response.addCookie(ck3);
		
		return "cookie/create";
	}

	@RequestMapping("delete")
	public String delete(HttpServletResponse response) {
		//쿠키는 삭제명령어가 따로 없음.
		//0초로 만료시간을 지정후 덮어쓰기를 진행하면 도니다.
		
		Cookie ck = new Cookie("test", "Leeee");
		ck.setMaxAge(0); //초단위 기준
		response.addCookie(ck);
		
		return "cookie/delete";
	}
}
