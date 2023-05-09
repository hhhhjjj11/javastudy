package com.ssafy.board.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import com.ssafy.board.model.dao.BoardDaoimpl;
import com.ssafy.board.model.dto.Board;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardDaoimpl dao = BoardDaoimpl.getInstance();
    
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (req.getMethod().equals("POST")) {
			req.setCharacterEncoding("UTF-8");
		}
		
		String act = req.getParameter("act");
		System.out.println("act" + act);
		
		switch(act) {
		case "list":
			doList(req, resp);
			break;
		case "writeform":
			doWriteForm(req, resp);
			break;
		case "write":
			doWrite(req, resp);
			break;
		case "detail":
			doDetail(req, resp);
		default:
			break;
		}
	}

	private void doDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(req.getParameter("id"));
		
		try {
			dao.updateViewCnt(id);
			req.setAttribute("board", dao.selectOne(id));
			req.getRequestDispatcher("/board/detail.jsp").forward(req, resp);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doWrite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Board board = new Board(); //껍데기 생성
		
		board.setTitle(req.getParameter("title"));
		board.setContent(req.getParameter("content"));
		board.setWriter(req.getParameter("writer"));
		
		try {
			dao.insertBoard(board);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 등록 했으면 1. 상세페이지로 가서 그 글의 내용을 보여주는 것
		//			2. 전체 글 목록으로 돌아가기
		
		// 전체글 목록으로 가기
		// doList(req,resp);  // 이렇게 하면 안됨. list화면에서 새로고침 할 때마다 추가됨.
		// 이유 : list화면에서 새로고침 -> get요청 -> service메서드에의해 doList()호출 -> doList는 포워드 메서드
		// req에 데이터가 남아있음 -> ?? 뭔소리임 이게?? 이해 안됨
		
		//resp.sendRedirect("board?act=list");
			
	}

	private void doWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/board/writeform.jsp").forward(req, resp);
	}

	private void doList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Board> list = dao.selectAll();
		
		// list라고 하는 이름으로 전체 게시글을 Dao에서 가져와 req에 넣어줌, 그리고 list.jsp로 포워딩
		req.setAttribute("list",list);
		req.getRequestDispatcher("/board/list.jsp").forward(req, resp); //기본경로는 webapp임
	}


	
}
