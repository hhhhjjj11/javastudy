package com.ssafy.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 다음과 같이 요청객체로부터 session데이터를 가져온다
		HttpSession session = request.getSession();
		// 만약 요청의 세션에 유저정보가 담겨잇다면 그것은 로그인이 되어있따는 말이고
		// 아니라면 로그인 되어있찌 않다는 뜻임
		// 로그인하면 id에 값을 담아두도록 했다고 치자.
		// 그러면 id의 값을 확인해보면 됨. 있으면 로그인 된거고 없으면 로그인 안된거 ㅇㅇ
		if(session.getAttribute("id") == null) {
			response.sendRedirect("login");
			return false;
		}
		
		return true; //오케이 통과
	}
}
