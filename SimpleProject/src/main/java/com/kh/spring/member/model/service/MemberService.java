package com.kh.spring.member.model.service;

import com.kh.spring.member.model.dto.MemberDTO;

public interface MemberService {

	// 로그인
	MemberDTO login(MemberDTO member);
	
	// 회원가입
	// 좋은 방법 : 가입된 회원 정보를 반환해준다. (Hibernate)
	// 일반적인 방법 : 정수값을 반환하거나 값을 반환하지 않는다. (Mybatis)
	MemberDTO signUp(MemberDTO member);
	
	// 회원정보수정 => int 말고 MemberDTO로 받아오는게 좋다
	// EX) 수정했는데 세션에 값은 그 전 아이디나 비밀번호일 수도 있다.
	// int로 보낼거면 HttpSession을 지금 넘겨받아서 여기서 Session에 담아야함.
	MemberDTO update(MemberDTO member);
	
	// 회원탈퇴
	int delete(MemberDTO member);
	
	// 1절 끝
	
	// 아이디 중복 체크
	
	// 2절 끝
	
	// 이메일인증은 시간나면 함.
	
}
