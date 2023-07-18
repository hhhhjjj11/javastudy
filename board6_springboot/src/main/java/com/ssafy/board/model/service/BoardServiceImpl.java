package com.ssafy.board.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.FileInfoDto;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		this.boardMapper = boardMapper;
	}

	/*
	 * 글작성 -> 요청으로부터 boardDto를 전달 받는다. 
	 * mapper메서드를 불러다가 쓴다.
	 * 전달받은 boardDto로부터 파일 정보를 확인한다
	 * 확인해서 만약에 파일정보가 있으면 매퍼메서드의 registerFile메서드를 이용해서 파일을 저장한다.
	 * */
	
	/*
	 * 파일 저장 기본 개념 : 사용자는 입력을 같이 하지만 저장은 따로함. 그리고 테이블을 연결시켜서 조회시 끌고오는방식으로 보여줄거임.
	 * 따라서 writeArticle과 registerFile은 따로쓰는거임.
	 * */
	@Override
	@Transactional
	public void writeArticle(BoardDto boardDto) throws Exception {
		System.out.println("글입력 전 dto : " + boardDto);
		boardMapper.writeArticle(boardDto);
		System.out.println("글입력 후 dto : " + boardDto);
		List<FileInfoDto> fileInfos = boardDto.getFileInfos();
		if (fileInfos != null && !fileInfos.isEmpty()) {
			boardMapper.registerFile(boardDto);
		}
	}

	@Override
	public List<BoardDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "b.user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);
		// 이렇게 하면 현재 페이지에서만 검색하는 것 같은데.. ㄴ
		// 일단 익히고 생각.
		return boardMapper.listArticle(param);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardMapper.getArticle(articleNo);
	}

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardMapper.updateHit(articleNo);
	}

	@Override
	public void modifyArticle(BoardDto boardDto) throws Exception {
		boardMapper.modifyArticle(boardDto);
	}

	@Override
	@Transactional
	public void deleteArticle(int articleNo, String path) throws Exception {
		List<FileInfoDto> fileList = boardMapper.fileInfoList(articleNo);
		boardMapper.deleteFile(articleNo);
		boardMapper.deleteArticle(articleNo);
		for(FileInfoDto fileInfoDto : fileList) {
			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
			file.delete();
		}
	}

}
