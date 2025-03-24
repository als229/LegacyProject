package com.kh.spring.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.reply.model.dto.ReplyDTO;

@Mapper
public interface BoardMapper {
	
	void insertBoard(BoardDTO board);
	
	@Select("SELECT COUNT(*) FROM TB_SPRING_BOARD WHERE STATUS = 'Y'")
	int selectTotalCount();
	
	List<BoardDTO> selectBoardList(RowBounds rowBounds);
	
	BoardDTO selectBoard(int board);
	
	List<ReplyDTO> selectReply(int boardNo);

	BoardDTO selectBoardAndReply(int boardNo);
	// --- 여기 까지 1저러러럴
	
	int updateBoard(BoardDTO board);
	
	int deleteBoard(int boardNo);
	
	List<BoardDTO> doSearch(RowBounds rowBounds, Map<String , Object> map);

	// 2절.... 
	int searchedCount(Map<String, Object> map);

	List<BoardDTO> selectSearchList(Map<String, Object> map, RowBounds rb);
	
	
	
}
