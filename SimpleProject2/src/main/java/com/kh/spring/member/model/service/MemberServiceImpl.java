package com.kh.spring.member.model.service;

import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberValidator memberValidator;
	private final MemberDAO memberDao;
	
	@Override
	public MemberDTO login(MemberDTO member) {
		
		// 1. 아이디 길이가 10개 넘으면 안됨
		memberValidator.validatedIdLengthCheck(member);
		
		// 2. 아이디나 비밀번호가 빈 값이 오면 안됨. 
		
		memberValidator.validatedNullIdPwCheck(member);
		// 3. 아이디나 비밀번호가 틀렸음
		
		
		return null;
	}

	@Override
	public void signUp(MemberDTO member) {
		
	}

	@Override
	public MemberDTO update(MemberDTO member) {
		return null;
	}

	@Override
	public int delete(MemberDTO member) {
		return 0;
	}


}
