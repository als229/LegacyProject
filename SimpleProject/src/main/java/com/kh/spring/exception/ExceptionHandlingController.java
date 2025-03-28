package com.kh.spring.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("message", errorMsg).setViewName("include/error_page");
		log.info("발생 예외 : {} ", errorMsg, e);
		return mv;
	}
	
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView runTimeError(TooLargeValueException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView runTimeError(InvalidParameterException e) {
		return createErrorResponse(e.getMessage(),e);
	}
	
	
	@ExceptionHandler(MemberNotFoundException.class)
	protected ModelAndView runTimeError(MemberNotFoundException e) {
		return createErrorResponse(e.getMessage(),e);
	}
	
	
	@ExceptionHandler(PasswordNotMatchException.class)
	protected ModelAndView runTimeError(PasswordNotMatchException e) {
		return createErrorResponse(e.getMessage(),e);
	}
	
	@ExceptionHandler(DuplicateIdException.class)
	protected ModelAndView runTimeError(DuplicateIdException e) {
		return createErrorResponse(e.getMessage(),e);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	protected ModelAndView runTimeError(AuthenticationException e) {
		return createErrorResponse(e.getMessage(),e);
	}
	
	@ExceptionHandler(MemberInsertFailException.class)
	protected ModelAndView runTimeError(MemberInsertFailException e) {
		return createErrorResponse(e.getMessage(),e);
	}
	
}
