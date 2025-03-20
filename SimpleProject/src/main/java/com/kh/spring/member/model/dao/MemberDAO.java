package com.kh.spring.member.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.member.model.dto.MemberDTO;

@Repository
public class MemberDAO {
	
	// login 과 checkId 가 같은 기능임
	public MemberDTO login(SqlSessionTemplate sqlSession, MemberDTO member) {
		return sqlSession.selectOne("memberMapper.login", member);
	}

	// 죽은메서드 => 구조를 잘 못 짬
	public int checkId(SqlSessionTemplate sqlSession, String memberId) {
		return sqlSession.selectOne("memberMapper.checkId", memberId);
	}

	public int signUp(SqlSessionTemplate sqlSession, MemberDTO member) {
		return sqlSession.insert("memberMapper.signUp", member);
	}

}
