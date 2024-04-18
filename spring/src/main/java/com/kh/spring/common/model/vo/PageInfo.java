package com.kh.spring.common.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageInfo {
	private int listCount; //현재 총 게시글 수
	private int currentPage; //현재 페이지(사용자가 요청한 페이지)
	private int pageLimit; //페이지 하단에 보여질 페이징바의 개수
	private int boardLimit; //한 페이지내에 보여질 게시글 최대갯수
	//위 4개의 값을 기준으로 아래 3개의 값을 구해야함
	
	private int maxPage; // 가장 마지막페이지(총 페이지의 수)		
	private int startPage; // 페이징바의 시작수
	private int endPage; // 페이징바의 마지막끝수
	
}
