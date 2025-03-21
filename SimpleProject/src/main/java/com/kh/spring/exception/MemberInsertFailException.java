package com.kh.spring.exception;

public class MemberInsertFailException extends RuntimeException {
	public MemberInsertFailException(String message) {
		super(message);
	}
}
