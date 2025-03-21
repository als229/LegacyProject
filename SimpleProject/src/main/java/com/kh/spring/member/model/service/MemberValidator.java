package com.kh.spring.member.model.service;


import org.springframework.stereotype.Component;

import com.kh.spring.exception.DuplicateIdException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {

	private final MemberMapper mapper;
	private final PasswordEncoder passwordEncoder;
	
	// 로그인 전 입력 값 확인
	private void validatedLength(MemberDTO member) {
		if(member.getMemberId().length() > 10) {
			throw new TooLargeValueException("아이디가 너무 길어유. 10자 이내로 작성하세유");
		}
	}
	
	// 로그인 전 입력값 비었는지 확이
	public void validtedValue(MemberDTO member) {
		if(member == null || 
			member.getMemberId() == null || 
			member.getMemberId().trim().isEmpty()|| 
			member.getMemberPw() == null || 
			member.getMemberPw().trim().isEmpty()) {
			throw new InvalidParameterException("올바르지 못한 값이다.");
		}
	}
	
	// 로그인 할 때 select 해서 같은 아이디 존재하는지 확인 없으면 없는 아이디 입력한거임
	public MemberDTO validateMemberExists(MemberDTO member) {
		
		MemberDTO loginMember = mapper.login(member);
		
		if(loginMember != null) {
			return loginMember;
		}
		
		throw new MemberNotFoundException("존재하지 않는 아이디");
	}
	
	// 비밀번호 일치하는지 확인 후 없으면 에러 있으면 멤버 객체 반환
	public MemberDTO validateMemberPwMatch(MemberDTO member) {
		MemberDTO loginMember = validateMemberExists(member);	// select 해서 같은 아이디 존재하는지 확인
		if(!passwordEncoder.matches(member.getMemberPw(), loginMember.getMemberPw())) {
			throw new PasswordNotMatchException("비밀번호 일치하지 않슴다");
		}
		
		return loginMember;
	}
	
	// 회원가입 할 때 아이디가 이미 존재 하는지 확인 
	private void validateDuplicatedId(MemberDTO member) {
		MemberDTO existingMember = mapper.login(member);
		if(existingMember != null && member.getMemberId().equals(existingMember.getMemberId())) {
			throw new DuplicateIdException("이미 존재하는 회원의 아이디이다.");
		}
	}

	
	// 로그인 할 때 쓰는 애
	public MemberDTO validatedLoginMember(MemberDTO member) {
		validatedLength(member);	// 로그인 전 입력 값 확인
		validtedValue(member);	// 로그인 전 입력값 비었는지 확인
		MemberDTO loginMember = validateMemberPwMatch(member);
		return loginMember;
	}
	
	// 회원가입 할 때 쓰는 애
	public void validatedJoinMember(MemberDTO member) {
		validatedLength(member);	// 로그인 전 입력 값 확인
		validtedValue(member);	// 로그인 전 입력값 비었는지 확인
		validateDuplicatedId(member); 	// 회원가입 할 때 아이디가 이미 존재 하는지 확인 
	}
	
}
