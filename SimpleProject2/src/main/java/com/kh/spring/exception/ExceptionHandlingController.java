package com.kh.spring.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(TooLargeValueException.class)
	public ModelAndView errorCheck(TooLargeValueException e) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("message", e.getMessage()).setViewName("include/error_page");
		log.info("에러 발생 데스 : {}", e.getMessage(), e);
		return mv;
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public ModelAndView errorCheck(InvalidParameterException e ) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("message", e.getMessage()).setViewName("include/error_page");
		log.info("에러 발생 데스 : {}", e.getMessage(), e);
		return mv;
	}
}
