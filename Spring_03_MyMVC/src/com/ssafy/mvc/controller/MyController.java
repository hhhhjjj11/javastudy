package com.ssafy.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.mvc.model.dto.User;
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
//	@RequestMapping("home")
	@RequestMapping(value="home", method= RequestMethod.GET)
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
	
	@GetMapping("test1")
	public String test1() {
		return "test1";
	}
	
	// 데이터를 같이 보내고 싶으면..
	// 바구니를 준비해라 ~~ 모델 바구니
	@GetMapping("test2")
	public String test2(Model model) {
		model.addAttribute("msg", "바구니에 데이터를 넣어서 보냈다.");
		return "test2";
	}
	
	// 파라미터로 여러개의 값을 받고 싶다.
	// 1. 우선 인자로 Model model이 필요함
	@GetMapping("test3")
	public String test3(Model model, String id, String pw) {
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);		
		return "test3";
	}
	
	// 적절한 DTO를 준비해놓으면 알아서 퐉 들어간다
	@PostMapping("test4")
	public String test4(Model model, User user) {
		
		System.out.println(user);
		
		return "test4";
	}
}
