package com.kh.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping("login")
	public String login(MemberDTO member) {
		
		
		memberService.login(member);
		
		
		
		return "redirect:/";
	}
	
}
