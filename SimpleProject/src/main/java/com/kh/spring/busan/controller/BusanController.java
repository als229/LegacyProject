package com.kh.spring.busan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.busan.model.service.BusanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*") // 이 포트로 오는 건 허가해준다.
@RequestMapping(value="busans", produces="application/json; charset=UTF-8")
@RequiredArgsConstructor
public class BusanController {
	
	private final BusanService busanService;
	
//	{
		// 스프링에서 응답할때 사용하는 친구
//		ResponseEntity 
//	}
	
	@GetMapping
	public ResponseEntity<String> getBusanFoods(@RequestParam(name="pageNo", defaultValue="1")int pageNo) {
		
		log.info("페이지 남바 : {}", pageNo);
		String responseData = busanService.requestGetBusan(pageNo);
		
		return ResponseEntity.ok(responseData);
	}
	
	
}
