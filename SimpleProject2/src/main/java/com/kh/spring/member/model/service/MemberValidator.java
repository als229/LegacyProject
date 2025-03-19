package com.kh.spring.member.model.service;


import org.springframework.stereotype.Component;

import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dto.MemberDTO;

@Component
public class MemberValidator {
	// 1. 아이디 길이가 10개 넘으면 안됨
	// 2. 아이디나 비밀번호가 빈 값이 오면 안됨. 
	// 3. 아이디나 비밀번호가 틀렸음
	
	public void validatedIdLengthCheck(MemberDTO member) {
		if(member.getMemberId().length() > 10) {
			throw new TooLargeValueException("아이디가 10글자 넘었어유");
		}
	}
	
	public void validatedNullIdPwCheck(MemberDTO member) {
		String id = member.getMemberId();
		String pw = member.getMemberPw();
		if(id == "" || id.trim().isEmpty() ||
			pw == "" || pw.trim().isEmpty()	
				) {
			throw new InvalidParameterException("아이디나 비밀번호 값이 없어유");
		}
	}
	
}
