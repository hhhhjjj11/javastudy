<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시글 전체 목록</title>
</head>
<body>
	<%@ include file="/common/header.jsp" %>
	<h2>게시글 목록</h2>
	<hr>
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>글쓴이</th>
			<th>조회수</th>
			<th>등록일</th>
		</tr>	
		<!-- ${list}로 넣으면 알아서 작은 스코프 부터 뒤져서 찾는다 --> 
		<c:forEach items="${list}" var="board">
			<tr>
				<td>${board.id}</td>
				<td>
					<a href="board?act=detail&id=${board.id}">
						${board.title}
					</a>
				</td>
				<td>${board.writer}</td>
				<td>${board.viewCnt}</td>
				<td>${board.regDate}</td>
				<td>${board.id}</td>
			</tr> 
		</c:forEach>
	</table>
	
	<a href="board?act=writeform">글등록0</a>
	<a href="/BackEnd07_Board_Web/board?act=writeform">글등록1</a>
	<a href="<%=request.getContextPath() %>/board?act=writeform">글등록2</a>
	<a href="${root}/board?act=writeform">글등록3</a>
	
</body>
</html>