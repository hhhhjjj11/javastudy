<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�۵��</title>
</head>
<body>
	<%@ include file="/common/header.jsp" %>
	<h2>�۵��</h2>
	<form action="board" method="POST">
		<input type="hidden" name="act" value="write">
		���� : <input type="text" name="title"> <br>
		���� : <input type="text" name="writer"> <br>
		���� : <textarea rows="10" cols="10" type="text" name="content"> </textarea><br>
		
		<button>�ۼ�</button>		
	
	</form>
</body>
</html>