package com.kh.spring.member.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	//로그인서비스
	public Member loginMember(Member m);
	
	//아이디 체크를 위한 서비스
	public int idCheck(String checkId);
}
