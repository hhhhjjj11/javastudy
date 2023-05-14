<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결과창</title>
</head>
<body>
	<a href="/file/upload/${fileName}">${fileName}</a>
	<!-- 컨텍스트 루트를 직접 적는건 별로 
	다음과 같이 pageContext.request.contextPath를 이용할 수 있다.
	-->
	<img src="${pageContext.request.contextPath}/upload/${fileName}">
	
	<!-- 다운로드 -->
	<a href="download?fileName=${fileName}">${fileName}다운로드</a>
	
	<c:forEach items="${list}" var="fileName">
		${fileName }<br>
	</c:forEach>
	
	
</body>
</html>