package com.ssafy.mvc.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@GetMapping("regist")
	public String regist() {
		return "regist";
	}
	
	@GetMapping("login")
	public String loginForm() {
		return "login";
	}
	
	@PostMapping("login")
	public String login(HttpSession session, String id, String pw) {
		
		//id와 pw를 이용해서 실제 로그인을 해야겠다.
		//실제로는 Service를 만들어서 Dao갔다가 DB갔다가... 해야겠지만
		// 우리는 인터셉터 실습해보는거니까 다음과 같이 간략하게 만들어서 해보자
		if(id.equals("ssafy") && pw.equals("1234")) {
			System.out.println("ddddd");
			System.out.println(session);
			System.out.println("2222");
			session.setAttribute("id", "ssafy");
			System.out.println(session.getAttribute("id"));
			return "redirect:/";
		}
		// 아니면 다시 로그인 페이지로..
		return "redirect:/login";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("id");
		return "redirect:/";
	}
}