package com.kh.spring.exception;

public class MemberNotFoundException  extends RuntimeException {
	public MemberNotFoundException(String message) {
		super(message);
	}
}
