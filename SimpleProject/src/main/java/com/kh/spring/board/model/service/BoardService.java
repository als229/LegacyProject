package com.kh.spring.board.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;

public interface BoardService {

	// 게시글 작성(파일 첨부)
	void insertBoard(BoardDTO board, MultipartFile file, HttpSession session);
	// 멀티 파트 방식 파일 전송 : 두가지 형태의 데이터를 전송 받는 방식 (파일형태 + 스트링)
	
	// 게시글 목록 조회
	Map<String, Object> selectBoardList(int currentPage);
	
	// 게시글 상세 보기(댓글도 같이 조회) => 새로운 멋잇는 기술이 뭘까요?
	BoardDTO selectBoard(int boardNo);
	
	// 게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	
	// 게시글 삭제(사실 업데이트 기능임)
	void deleteBoard(int boardNo);

	Map<String, Object> doSearch(Map<String, String> map);

	int insertReply(ReplyDTO reply, HttpSession session);
	
	///////////// 1절 ////////////////// 
	// 게시글 검색 기능
	
	// 댓글 작성
	
}
