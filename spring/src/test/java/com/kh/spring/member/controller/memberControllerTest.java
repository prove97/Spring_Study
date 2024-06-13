package com.kh.spring.member.controller;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/test/resources/root-context.xml",
		"file:src/test/resources/servlet-context.xml",
		"file:src/test/resources/spring-security.xml"
})

public class memberControllerTest {
	
	@Autowired
	private WebApplicationContext wac; //웹어플리케이션 컨텍스트
	private MockMvc mockMvc; //HTTP요청 및 응답을 모의로 테스트 할 수 있는 객체
	
	@Before // Test객체가 생성되기 전에 실행하는 메소드
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); //mockMvc에 컨텍스트 정보를 저장
		log.info("mockMvc 준비완료");
	}

	@Test
	public void testLoginMember() {
		try {
			mockMvc.perform(post("/login.me") // POST 메소드로 /login.me 요청
					.param("userId", "admin") //요청 파라미터 userId 세팅
					.param("userPwd", "1234"))
			.andDo(print()) // 요청에 대한 응답코드를 콘솔에 출력해줘
			.andExpect(status().isOk()); // HTTP 상태코드가 200 ok인지 확인해줘
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void testLogoutMember() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testEnrollForm() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIdCheck() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testMyPage() {
//		fail("Not yet implemented");
//	}

}
