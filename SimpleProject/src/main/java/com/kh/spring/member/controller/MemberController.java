package com.kh.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.spring.member.model.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j  // lombok 기능 
@Controller
public class MemberController {
	
	// 1번 방법
	/*
	@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		log.info("id : {}, pw : {}", id, pw);
		
		return "main_page";
	}
	*/
	
	// 2번 방법
	/*
	@RequestMapping("login")
	public String login(@RequestParam(value="id", defaultValue="혹시 값이 안넘어 오면 기본 값으로 셋팅") String id,
						@RequestParam(value="pw") String pw) {
		log.info("두 번째 방법으로 뽑기 id : {}, pw : {}",id, pw);
		
		return "main_page";
	}
	*/
	
	// 3번 방법
	/*
	@PostMapping("login")
	public String login(String id, String pw) {
		
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);
		
		
		
		
		return "main_page";
	}
	*/
	// 3번이 편하지만 2번이 권장된다.
	
	// 4번
	/**
	 * 커맨드 객체 방식
	 * 
	 * 전제 조건
	 * 1. 매개 변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 키 값과 객체의 필드명이 동일할 것
	 * 3. setter 메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 통해서 생성한 후 내부적으로 setter 메서드를 찾아서 요청 시 전달값을 해당 필드에 대입해줌
	 * (Setter Injection)
	 */
	@PostMapping("login")
	public String login(MemberDTO member) {
		
		log.info("이게되네 ~ {}", member);
		
		return "main_page";
	}
	
}
