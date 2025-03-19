package com.kh.spring.member.model.service;

import java.security.InvalidParameterException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberDAO;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberDAO memberDao;
	private final SqlSessionTemplate sqlSession;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	private final MemberValidator validator;
	
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
		
		/*
		 * SqlSession sqlSession = new SqlSession();
		 * MemberDTO loginMember = new MemberDAO().login(sqlSession, member);
		 * sqlSession.close();
		 * 
		 * => Injection Of Control(IOC) : 제어의 역전, 열고 닫는 생명 주기 관리를 스프링이 해준다.
		 * 
		 * retrun loginMember;
		 */
		validator.validatedLoginMember(member);
		
		// 1. Table에 아이디가 존재해야한다.
		// 2. 비밀번호가 일치 해야한다.
		// 3. 둘 다 통과하면 정상적으로 조회할 수 있도로 해주어야 한다.
		
		MemberDTO loginMember = memberDao.login(sqlSession, member);
		// 아이디만 일치해도 행의 정보를 가지고 오게 됨.
		
		// 1. loginMember가 null 값과 동일하다면 아이디가 존재하지 않는다.
		validator.validatedLoginMember(loginMember);
		
		// 2. 아이디만 가지고 조회를 하기 때문에
		// 비밀번호를 검증 후 비밀번호가 유효하다면 회원의 정보를 session에 담기
		// 비밀번호가 유효하지 않다면 비밀번호 이상한데?
		
		boolean flag = bcryptPasswordEncoder.matches(member.getMemberPw(), loginMember.getMemberPw());
		
		if (flag) {
			return loginMember;
		} else {
			throw new PasswordNotMatchException("비밀번호 일치하지 않슴다");
		}
	}

	@Override
	public void signUp(MemberDTO member) {
		
		if(member == null || 
				member.getMemberId() == null || 
				member.getMemberId().trim().isEmpty()|| 
				member.getMemberPw() == null || 
				member.getMemberPw().trim().isEmpty()) {
				return;
		}

		if(member.getMemberId().length() > 10) {
			return;
		}
		
		int result = memberDao.checkId(sqlSession, member.getMemberId());
		
		if(result > 0) { return; }
		
		// INSERT
		// memberPw 가 평문(plain text)임. => 값이 다 보임 
//		log.info("사용자가 입력한 비밀번호 평문 : {}", member.getMemberPw());
//		
//		log.info("평문을 암호문으로 바꾼 것 {}", bcryptPasswordEncoder.encode(member.getMemberPw()));
		String encPwd = bcryptPasswordEncoder.encode(member.getMemberPw());
		member.setMemberPw(encPwd);
		
		int insertResult = memberDao.signUp(sqlSession, member);
		
		if(insertResult > 0) {
			return;
		} else {
			return;
		}
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
