<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�󼼺���</title>
</head>
<body>
	<h2>�� �󼼺���</h2>
	<hr>
	<h4>${board.title}</h4> 
	<div>
		��ȸ�� : ${board.viewCnt }
	</div>
	<div>
		�۽��� : ${board.writer }
	</div>
	<div>
		����� : ${board.regDate }
	</div>
	<div>
		���� : ${board.content}
	</div>
	
</body>
</html>