package com.kh.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
//	@GetMapping("boards")
//	public String toBoardList() {
//		return "board/board_list";
//	}
	
	@GetMapping("boards")
	public String toBoardList(@RequestParam(name="page", defaultValue="1") int page,
								Model model
			) {
		
		// 한 페이지 몇 개 보여줄까 ? 5
		// 버튼 몇 개 보여줄까? 5
		// 총 게시글 개수 == SELECT COUNT(*) FROM TB_STRING_BOARD WHERE STATUS = 'Y;
		if(page < 1) {
			throw new InvalidParameterException("1보다 작은 페이지는 읎어 ~");
		}
		
		Map<String, Object> map = boardService.selectBoardList(page);
		
		model.addAttribute("map", map);
		
		return "board/board_list";
	}
	
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_board";
	}
	
	@PostMapping("boards")
	public ModelAndView newBoard(ModelAndView mv, BoardDTO board
								, MultipartFile upfile
								, HttpSession session) {
		
		/*
		 * MultipartFile 로 주고받기!!!!
		 * 1. 앞단  
		 * <form id="enrollForm" method="post" action="boards" enctype="multipart/form-data">
		 * form 태그에  enctype="multipart/form-data" 추가해줘야함
		 * 
		 * 2. view 에 설정한 file name과 받을 때 name 이 같아야함. 
		 * 	view 에서 name upfile, 컨트롤러에서 받을때 upfile로 받아야 함
		 */
		
		log.info("게시글 정보 : {}, 파일 정보 : {}", board, upfile);
		
		// 첨부파일 존재 유무
		// => 차이점 => MultipartFile 타입의 filename 필드 값으로 확인을 하겠다.
		
		// INSERT INTO TB_SPRING_BOARD(BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER, CHANGE_NAME) VALUES(#{boardTitle}, #{boardContent}, #{boardWriter}, #{changeName})
		
		// 1. 권한 있는 요청인가 확인
		// 2. 값들이 유효성이 있는 값인가
		// 3. 전달된 파일이 존재할 경우 => 파일명 수정 서버에 올리고 BoardDTO 의 changeNmae 필드에 값을 대입
		boardService.insertBoard(board, upfile, session);
		
		mv.setViewName("redirect:boards");
		session.setAttribute("message", "게시글 등록 성공~~~~");
		
		return mv;
	}
	
	@GetMapping("boards/{id}")
	public ModelAndView goBoard(ModelAndView mv, @PathVariable(name="id") int boardNo) {
		
		// 1. 잘 넘어오는지 확인
		// log.info("게시글 번호 : {}", boardNo);
		
		if(boardNo < 1) {
			throw new InvalidParameterException("보드 디테일 쪽 비정상적인 접근이야~~~~~~~~~");
		}
		
		BoardDTO board = boardService.selectBoard(boardNo);
		
		mv.addObject("board", board).setViewName("board/board_detail");
		
		return mv;
	}
	
	@GetMapping("search")
	public ModelAndView doSearch(@RequestParam(name="condition") String condition,
								@RequestParam(name="keyword") String keyword,
								@RequestParam(name="page",defaultValue="1") int page,
								ModelAndView mv
			) {
		
		Map<String , String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		map.put("currentPage", String.valueOf(page));
		
		Map<String, Object> list = boardService.doSearch(map);
		
		list.put("condition", condition);
		list.put("keyword", keyword);
		
		mv.addObject("map", list).setViewName("board/board_list");
		
		return mv;
	}

}
