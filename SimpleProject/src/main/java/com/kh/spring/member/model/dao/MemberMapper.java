package com.kh.spring.member.model.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.kh.spring.member.model.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
	@Select("		SELECT\r\n"
			+ "			  MEMBER_ID memberId\r\n"
			+ "			, MEMBER_PW memberPw\r\n"
			+ "			, MEMBER_NAME memberName\r\n"
			+ "			, EMAIL email\r\n"
			+ "			, ENROLL_DATE enrollDate\r\n"
			+ "		FROM\r\n"
			+ "			KH_MEMBER\r\n"
			+ "		WHERE\r\n"
			+ "			MEMBER_ID = #{ memberId }")
	MemberDTO login(MemberDTO member);
	
	@Insert("		INSERT\r\n"
			+ "			INTO\r\n"
			+ "				KH_MEMBER\r\n"
			+ "		VALUES\r\n"
			+ "			(\r\n"
			+ "				  #{ memberId }\r\n"
			+ "				, #{ memberPw }\r\n"
			+ "				, #{ memberName }\r\n"
			+ "				, #{ email }\r\n"
			+ "				, SYSDATE\r\n"
			+ "			)")
	int signUp(MemberDTO member);
	
	@Update("UPDATE KH_MEMBER SET MEMBER_NAME = #{ memberName } , EMAIL = #{ email } WHERE MEMBER_ID = #{ memberId } ")
	int update(MemberDTO member);
	
	int delete(MemberDTO member);
}
