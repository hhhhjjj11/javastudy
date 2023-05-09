package com.ssafy.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.mvc.model.service.MyService;

@Controller
public class MyController {
	
	//컨트롤러에서 서비스객체 만듬. 서비스 객체의 메소드를 이용할거임.
	private MyService myService;
	
	// 서비스객체 생성시 인자로 마이서비스 객체를 넣어서 설정자를 설정한다.
	// autowired 어노테이션을 붙임 -> MyService클래스에 @Service어노테이션이 붙어있기 때문에
	// 해당 경로에서 어노테이션이 붙은 애들을 자동으로 bean객체를 만들어 줌
	// 자동으로 의존성을 주입해줌 @Autowired붙였고, @Service붙였으므로.
	@Autowired
	public void setMyService(MyService myService) {
		this.myService = myService;
	}
	
	// "home"이라는 요청이 들어오면 처리 
	// ModelAndView의 객체를 반환하는 메서드를 정의
	@RequestMapping("home")
	public ModelAndView homeHandle() {
		System.out.println("ddd");
		ModelAndView mav = new ModelAndView();
		System.out.println("home 요청이  왔다.");
		
		// mav.addObject("키", "밸류")
		mav.addObject("msg", "Welcome to Spring World");
		
		myService.doSomething();
		// view 이름을 지정 해준다.
		mav.setViewName("home");
		
		return mav;
	}
}
