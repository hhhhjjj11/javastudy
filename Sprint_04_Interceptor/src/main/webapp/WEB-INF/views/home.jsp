<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Home</title>
</head>
<body>
	<c:choose>
		<c:when test="${empty id}">
			<a href="login">로그인페이지</a>
		</c:when>
		<c:otherwise>
			${id}님 반갑습니다. <a href="logout">로그아웃</a>
		</c:otherwise>
	</c:choose>
	<h1>
		Hello world!  
	</h1>

	<P>  The time on the server is ${serverTime}. </P>

	<!-- regist.jsp로 보내는 버튼을 하나 만들고 싶다. -->
	<a href="regist">regist.jsp로 가즈아 </a>
</body>
</html>
