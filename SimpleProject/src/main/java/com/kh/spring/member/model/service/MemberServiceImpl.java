package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberDAO memberDao;
	private final SqlSessionTemplate sqlSession;
	
//	@Autowired
//	public MemberServiceImpl(MemberDAO memberDao, SqlSessionTemplate sqlSession) {
//		this.memberDao = memberDao;
//		this.sqlSession = sqlSession;
//	}
	
	@Override
	public MemberDTO login(MemberDTO member) {

		// 로그인 처리
		
		// 유효성 검사
		// 아이디가 10자가 넘으면 안되겠네??
		// 아이디/비밀번호 : 빈 문자열 또는 null 이면 안되겠네?
		if(member == null || 
			member.getMemberId() == null || 
			member.getMemberId().trim().isEmpty()|| 
			member.getMemberPw() == null || 
			member.getMemberPw().trim().isEmpty()) {
			return null;
		}

		if(member.getMemberId().length() > 10) {
			return null;
		}
		
		/*
		 * SqlSession sqlSession = new SqlSession();
		 * MemberDTO loginMember = new MemberDAO().login(sqlSession, member);
		 * sqlSession.close();
		 * 
		 * => Injection Of Control(IOC) : 제어의 역전, 열고 닫는 생명 주기 관리를 스프링이 해준다.
		 * 
		 * retrun loginMember;
		 */
		
		// 1. Table에 아이디가 존재해야한다.
		// 2. 비밀번호가 일치 해야한다.
		// 3. 둘 다 통과하면 정상적으로 조회할 수 있도로 해주어야 한다.
		
		MemberDTO loginMember = memberDao.login(sqlSession, member);
		
		return loginMember;
	}

	@Override
	public MemberDTO signUp(MemberDTO member) {
		return null;
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
