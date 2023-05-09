 package com.ssafy.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyController {


	
//	"home"이라는 요청이 들어오면 처리 
	@RequestMapping("home")
	public ModelAndView homeHandle() {
		System.out.println("ddd");
		ModelAndView mav = new ModelAndView();
		System.out.println("home 요청이  왔다.");
		
		//mav.addObject("키", "밸류")
		// mav.addObject("msg", "Welcome to Spring World");
		
		// view 이름을 지정 해준다.
		// mav.setViewName("home");
		
		return mav;
	}
}
