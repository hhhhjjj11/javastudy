package com.ssafy.vue.model.service;

import java.util.Map;

public interface JwtService {
	/* 1. 어세스토큰 생성
	 * 2. 리프레시토큰 생성
	 * 3. 토큰생성?
	 * 4. get?
	 * 5. 유저아이디
	 * 6. 토큰유효성확인
	 * */
	<T> String createAccessToken(String key, T data);
	<T> String createRefreshToken(String key, T data);
	<T> String create(String key, T data, String subject, long expir);
	Map<String, Object> get(String key);
	String getUserId();
	boolean checkToken(String jwt);
	
}
