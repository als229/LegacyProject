package com.kh.spring.member.model.dto;

import java.sql.Date;

import lombok.Data;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
// 원래 필요한거만 만들어서 써야함.
// Data는 종합 선물세트 
@Data
public class MemberDTO {
	
	private long memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String email;
	private Date enrollDate;
	
}
