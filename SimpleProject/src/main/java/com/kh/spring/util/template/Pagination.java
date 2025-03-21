package com.kh.spring.util.template;

import com.kh.spring.util.model.dto.PageInfo;

public class Pagination {

	public static PageInfo getPageInfo(int count,
									   int currentPage,
									   int boardLimit,
									   int pageLimit) {
		
		int maxPage = (int)Math.ceil((double)count / boardLimit);
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit -1;
		
		if(endPage > maxPage) { endPage = maxPage; };
		
		
		// return new PageInfo(count, currentPage, boardLimit, pageLimit, maxPage, startPage, endPage);
		return PageInfo.builder()
				.boardLimit(boardLimit)
				.count(count)
				.currentPage(currentPage)
				.startPage(startPage)
				.endPage(endPage)
				.maxPage(maxPage)
				.pageLimit(pageLimit).build();
		// => 빌드 패턴 내가 원하는 값을 메서드 체이닝으로 넣어서 순서가 섞여도 상관없이 setter처럼 할 수 있다.
	}
}
