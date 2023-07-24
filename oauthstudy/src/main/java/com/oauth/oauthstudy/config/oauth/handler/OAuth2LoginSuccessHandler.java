package com.oauth.oauthstudy.config.oauth.handler;

import com.oauth.oauthstudy.Service.JwtService;
import com.oauth.oauthstudy.config.oauth.provider.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("OAuth2 Login 성공, LoginSuccessHandler 호출");
        try{

            KakaoUserInfo kakaoUserInfo =(KakaoUserInfo)authentication.getPrincipal();
            loginSucces(response, kakaoUserInfo);
        } catch (Exception e){
            throw e;
        }
    }

    private void loginSucces(HttpServletResponse response, KakaoUserInfo kakaoUserInfo) {
        String accessToken = jwtService.creaetAccessToken(kakaoUserInfo.getName());
    }
}
