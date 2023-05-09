<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>상세보기</title>
</head>
<body>
	<h2>글 상세보기</h2>
	<hr>
	<h4>${board.title}</h4> 
	<div>
		조회수 : ${board.viewCnt }
	</div>
	<div>
		글슨이 : ${board.writer }
	</div>
	<div>
		등록일 : ${board.regDate }
	</div>
	<div>
		내용 : ${board.content}
	</div>
	
</body>
</html>