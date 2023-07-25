package com.oauth.oauthstudy.controller;


import com.oauth.oauthstudy.config.auth.PrincipalDetails;
import com.oauth.oauthstudy.domain.member.Member;
import com.oauth.oauthstudy.domain.member.Role;
import com.oauth.oauthstudy.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(exposedHeaders = "Authorization")
public class IndexController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // 요청객체로부터 세션에있는 로그인 유저 정보에 접근하는 방법은 다음과 같다.
    // 방법은 2가지이다. authentication 객체를 이용하거나
    // @AuthenticationPrincipal 어노테이션을 써서 바로 userDetails를 불러올 수도 있다.
    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userdetails){
        System.out.println("/test/login=======================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication :"+principalDetails.getMember());

        System.out.println("userDetails: "+userdetails.getMember());
        return "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth){
        System.out.println("/test/oauth/login=======================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication :"+oAuth2User.getAttributes());
        System.out.println("oauth2User: "+oauth.getAttributes());

        return "OAuth세션 정보 확인";
    }

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
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails: "+principalDetails.getMember());
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

        member.setRole(Role.USER);
        String rawPassword = member.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        member.setPassword(encPassword);
        memberRepository.save(member);   // 비밀번호 암호화 해야됨. 바로저장하면 안됨.
        return "redirect:/loginForm";
    }
}
