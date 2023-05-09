package com.ssafy.board.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.board.model.dto.Board;
import com.ssafy.board.util.DBUtil;


public class BoardDaoimpl implements BoardDao {

	// DButil 가져와야 함.
	private final DBUtil util = DBUtil.getInstance();
	
	private static BoardDaoimpl instance = new BoardDaoimpl();
	private BoardDaoimpl() {
		
	}
	
	public static BoardDaoimpl getInstance() {
		return instance;
	}
	
	
	@Override
	public List<Board> selectAll() {
		List<Board> list = new ArrayList<>();
		
		// 선언을 try블록 안에서 해버리면 scope이슈로 finally블록에서 사용 못하므로,
		// 밖에서 선언하고, try블록 안에서 값을 할당
		// 밖에서 할당하면 안되나 ?  또 예외처리하라고 뜨기 때문에 이런식으로 한다.
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = util.getConnection();
			
			stmt = conn.createStatement();
			
			// 전체 값을 가져오는 sql문 작성
			String sql = "SELECT * FROM board";
					
			rs = stmt.executeQuery(sql); // 쿼리가 반환하는 데이터 집합임.
			
			//데이터가 여러개일 수 있으므로 while이용
			while(rs.next()) {
				Board board = new Board();
				// rs.getInt("id"); // resultSet 에서 데이터 하나를 고를거임 getInt메서드를 이용해서 필드의값이 정수인
				board.setId(rs.getInt("id"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getString("reg_date"));
				board.setViewCnt(rs.getInt("view_cnt"));
				
				list.add(board);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs,stmt,conn); 
		}
		
		return list;
	}

	@Override
	public Board selectOne(int id) throws SQLException {
		String sql = "SELECT * FROM board WHERE id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Board board = new Board();
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 필드 인덱스랑 설정자랑 잘 맞아야됨!! 안그러면오류남
				board.setId(rs.getInt(1));
				board.setWriter(rs.getString(2));
				board.setTitle(rs.getString(3));
				board.setContent(rs.getString(4));
				board.setViewCnt(rs.getInt(5));							
				board.setRegDate(rs.getString(6)); 
			}
			
		} finally {
			util.close(rs, pstmt, conn);
		}
		return board;
	}

	@Override
	public void insertBoard(Board board) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO board (title, writer, content) VALUES (?,?,?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		conn = util.getConnection();
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, board.getTitle());
		pstmt.setString(2, board.getWriter());
		pstmt.setString(3, board.getContent());
		
		pstmt.executeUpdate();
		
		util.close(pstmt, conn);
	}

	@Override
	public void deleteBoard(int id) throws SQLException {
		String sql = "DELETE FROM board where id=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			
			System.out.println(result + "개의 데이터가 지워졌습니다");	
		} finally {
			util.close(pstmt, conn);
		}
		
	}

	@Override
	public void updateBoard(Board board) throws SQLException {
		String sql = "UPDATE board SET title = ?, content =? WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getId());
		
			pstmt.executeUpdate();
			
		} finally {
			util.close(pstmt, conn);
		}
		 
	}

	@Override
	public void updateViewCnt(int id) throws SQLException {
	
		String sql = "UPDATE board SET view_cnt = view_cnt+1 WHERE id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} finally {
			util.close(pstmt, conn);
		}
	}

}
