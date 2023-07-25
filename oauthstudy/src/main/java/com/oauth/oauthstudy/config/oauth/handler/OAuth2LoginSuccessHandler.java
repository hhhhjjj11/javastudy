package com.oauth.oauthstudy.config.oauth.handler;

import com.oauth.oauthstudy.Service.JwtService;
import com.oauth.oauthstudy.config.auth.PrincipalDetails;
import com.oauth.oauthstudy.config.oauth.provider.KakaoUserInfo;
import com.oauth.oauthstudy.domain.member.Role;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("OAuth2 Login 성공, LoginSuccessHandler 호출");
        PrincipalDetails principalDetails =(PrincipalDetails)authentication.getPrincipal();
        loginSuccess(response, principalDetails);
        response.sendRedirect("/");
//        try{
//            if (principalDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_GUEST"))) {
//                // "ROLE_GUEST"가 포함되어 있을 경우
//                String accessToken = jwtService.createAccessToken(principalDetails.getUsername());
//                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
//                response.sendRedirect("oauth2/sign-up"); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
//                // "ROLE을 USER로 수정해주기"
//                // 닉네임을 설정시 role또한 user로 수정하기
//                //
//            }
//            else{
//                loginSuccess(response, principalDetails);
//            }
//        } catch (Exception e){
//            throw e;
//        }
    }

    private void loginSuccess(HttpServletResponse response, PrincipalDetails principalDetails) throws IOException {
        System.out.println("loginSuccess Method 호출");
        String accessToken = jwtService.createAccessToken(principalDetails.getUsername());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);
        System.out.println("accessToken: "+accessToken);
        System.out.println("refreshToken: "+refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        System.out.println("principalDetails.getUsername() : "+principalDetails.getUsername());
        jwtService.updateRefreshToken(principalDetails.getUsername(), refreshToken);
        System.out.println("response!@#"+response.getHeader("Authorization"));
    }
}
