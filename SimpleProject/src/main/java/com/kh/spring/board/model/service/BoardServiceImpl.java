package com.kh.spring.board.model.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;
import com.kh.spring.util.model.dto.PageInfo;
import com.kh.spring.util.template.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;

	@Override
	public void insertBoard(BoardDTO board, MultipartFile file, HttpSession session) {
		
		// 1. 권한 체크
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		
		if(loginMember != null && !loginMember.getMemberId().equals(board.getBoardWriter())) {
			throw new AuthenticationException("권한없는 접근데스");
		}
		
		// 2. 유효성 검사
		if(board.getBoardTitle() == null || board.getBoardTitle().trim().isEmpty() ||
			board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() ||
			board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
			throw new InvalidParameterException("유효하지 않은 요청");
		}
		
		// 2_2) 
		
		// 3) 파일 유무 체크 // 이름 바꾸기 + 저장
		if(!file.getOriginalFilename().isEmpty()) {
			
			// 이름 바꾸기 
			
			// kh_ + 현재 시간 + 랜덤숫자 + 원본 파일 확장자
			StringBuilder sb = new StringBuilder();
			
			sb.append("kh_");
//			log.info("현재 시간 : {} ",new Date()); // 현재 시간 : Fri Mar 21 10:16:28 KST 2025 
			String crrentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//			log.info(crrentTime); // 20250321101628
			sb.append(crrentTime);
			sb.append("_");
			int random = (int)(Math.random()*900) + 100;
			sb.append(random);
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			sb.append(ext);
			
			log.info("바뀐 이름 ~ : {}", sb.toString());
			
			// 외부의 upload_files 에 접근해야 하기 때문에 ApplicationScope 단위 접근이 필요하다
			// 그래서 객체가 필요 한데 getServletContext 를 보면 application 어쩌구가 있다.
			ServletContext application =session.getServletContext();
			
			// upload_files 밑에 저장하겠다.
			String savePath = application.getRealPath("/resources/upload_files/");
			
			// transferTo 란 무엇일까
			try {
				file.transferTo(new File(savePath + sb.toString()));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			board.setChangeName("/spring/resources/upload_files/" + sb.toString());
		}
		
		boardMapper.insertBoard(board);

	}

	@Override
	public Map<String, Object> selectBoardList(int currentPage) {
		
		List<BoardDTO> boards = new ArrayList();
		Map<String, Object> map = new HashMap();
		
		int count = boardMapper.selectTotalCount();

		PageInfo pi = Pagination.getPageInfo(count, currentPage, 5, 5);
		
		if(count != 0) {
			RowBounds rb = new RowBounds((currentPage -1) * 5, 5);
			boards = boardMapper.selectBoardList(rb);
		}
		
		map.put("boards", boards);
		map.put("pageInfo", pi);
		
		return map;
	}

	@Override
	public BoardDTO selectBoard(int boardNo) {
		
		// 1절
//		BoardDTO board = boardMapper.selectBoard(boardNo);
//		
//		if(board == null) {
//			throw new InvalidParameterException("존재하지 않는 게시글 데스");
//		}
//		// 2절
//		List<ReplyDTO> replyList = boardMapper.selectReply(boardNo);
//		board.setReplyList(replyList);
		
		// 3절
		BoardDTO board = boardMapper.selectBoardAndReply(boardNo);
		
		if(board == null) {
			throw new InvalidParameterException("존재하지 않는 게시글 데스");
		}
		
		return board;
	}

	@Override
	public BoardDTO updateBoard(BoardDTO board, MultipartFile file) {
		return null;
	}

	@Override
	public void deleteBoard(int boardNo) {
		
	}


}
