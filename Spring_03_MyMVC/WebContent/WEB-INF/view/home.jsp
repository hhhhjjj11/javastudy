<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>home</title>
</head>
<body>
	<h2>환영합니다. 처음으로 오셨네요</h2>
	${ msg }
	
	<form action="test4" method="POST">
		아디 : <input type="text" name="id">
		비번 : <input type="text" name="pw">
		나이 : <input type="number" name="age">
		<input type="submit">
	</form>
</body>
</html>