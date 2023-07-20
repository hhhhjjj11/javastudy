package com.oauth.oauthstudy.controller;


import com.oauth.oauthstudy.domain.member.Member;
import com.oauth.oauthstudy.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String index(){
        System.out.println("d===============================");
        return "index";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){

        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String join(){
        return "joinForm";
    }
    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "joinProc";
    }

    @PostMapping("/join")
    public @ResponseBody String join2(Member member){
        System.out.println("회원가입 요청!!!!!");
        System.out.println(member);

        member.setRole("ROLE_USER");
        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setPassword(encPassword);
        memberRepository.save(member);   // 비밀번호 암호화 해야됨. 바로저장하면 안됨.
        return "redirect:/loginForm";
    }
}
