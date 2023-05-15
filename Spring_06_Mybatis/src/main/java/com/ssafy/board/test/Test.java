package com.ssafy.board.test;

import com.ssafy.board.config.MyAppSqlConfig;
import com.ssafy.board.model.dao.BoardDao;

public class Test {
	public static void main(String[] args) {
		// 이렇게 하면 마이바티스가 dao를 구현해서 구현체를 반환해줌. getMapper를 이용한다는 점.
		// 이렇게 하면 전에 했던 것 처럼 DaoImpl클래스를 따로 만들 필요가 없게됨.
		BoardDao dao = MyAppSqlConfig.getSession().getMapper(BoardDao.class);
	}
}