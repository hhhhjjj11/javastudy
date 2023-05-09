<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>글등록</title>
</head>
<body>
	<%@ include file="/common/header.jsp" %>
	<h2>글등록</h2>
	<form action="board" method="POST">
		<input type="hidden" name="act" value="write">
		제목 : <input type="text" name="title"> <br>
		쓰니 : <input type="text" name="writer"> <br>
		내용 : <textarea rows="10" cols="10" type="text" name="content"> </textarea><br>
		
		<button>작성</button>		
	
	</form>
</body>
</html>