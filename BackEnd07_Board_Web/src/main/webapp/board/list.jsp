<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�Խñ� ��ü ���</title>
</head>
<body>
	<%@ include file="/common/header.jsp" %>
	<h2>�Խñ� ���</h2>
	<hr>
	<table>
		<tr>
			<th>��ȣ</th>
			<th>����</th>
			<th>�۾���</th>
			<th>��ȸ��</th>
			<th>�����</th>
		</tr>	
		<!-- ${list}�� ������ �˾Ƽ� ���� ������ ���� ������ ã�´� --> 
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
	
	<a href="board?act=writeform">�۵��0</a>
	<a href="/BackEnd07_Board_Web/board?act=writeform">�۵��1</a>
	<a href="<%=request.getContextPath() %>/board?act=writeform">�۵��2</a>
	<a href="${root}/board?act=writeform">�۵��3</a>
	
</body>
</html>