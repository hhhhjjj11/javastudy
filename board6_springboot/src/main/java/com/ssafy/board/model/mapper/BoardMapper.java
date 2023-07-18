package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {
	
	/* 게시글 작성 / 파일등록 / 게시글전체조회 / 게시글개수 몇개인지 조회 / 게시글 상세조회 / 
	 * 게시글 수정 / 게시글 삭제 / 파일삭제 / 조회수 증가 /
	 * */
	
	// db를 거치는 경우 SQLException
	// 안거치는 경우 걍 Exception인데 
	// deleteFile이랑 fileInfoList의 경우 안거치나보네???? 확인필요.
	
	int writeArticle(BoardDto boardDto) throws SQLException;
	void registerFile(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, Object> map) throws SQLException;
	int getTotalArticleCount(Map<String, Object> map) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	void modifyArticle(BoardDto boardDto) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	void deleteArticle(int articleNo) throws SQLException;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
}
