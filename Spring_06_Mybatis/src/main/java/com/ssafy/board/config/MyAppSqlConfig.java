package com.ssafy.board.config;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;

public class MyAppSqlConfig {
	private static SqlSession session;
	
	static {
		try {
			//MyBatis 설정파일을 가져오기
			
			// sqlsessionfactory 객체 만들기
			String resource = "config/mybatis-config.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); 
			
			// 팩토리 이용해서 세션인스턴스 ㄱㄱ
			session = sqlSessionFactory.openSession(true);
			System.out.println("세션 생성 성공");
		} catch (IOException e) {
			System.out.println("세션 생성 실패");
		}
		
	}
	
	public static SqlSession getSession() {
		return session;
	}
}
