package com.kh.spring.member.model.service;


import org.springframework.stereotype.Component;

import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dto.MemberDTO;

@Component
public class MemberValidator {

	public void validatedLength(MemberDTO member) {
		if(member.getMemberId().length() > 10) {
			throw new TooLargeValueException("아이디가 너무 길어유. 10자 이내로 작성하세유");
		}
	}
	
	public void validtedValue(MemberDTO member) {
		if(member == null || 
			member.getMemberId() == null || 
			member.getMemberId().trim().isEmpty()|| 
			member.getMemberPw() == null || 
			member.getMemberPw().trim().isEmpty()) {
			throw new InvalidParameterException("올바르지 못한 값이다.");
		}
	}
	
	public void voidatedNullCheck(MemberDTO member) {
		if (member == null) {
			throw new MemberNotFoundException("존재하지 않는 아이디");
		}
	}
	
	public void validatedLoginMember(MemberDTO member) {
		voidatedNullCheck(member);
		validatedLength(member);
		validtedValue(member);
	}
	
}
