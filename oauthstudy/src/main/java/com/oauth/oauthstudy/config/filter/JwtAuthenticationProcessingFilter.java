package com.oauth.oauthstudy.config.filter;

import com.oauth.oauthstudy.Service.JwtService;
import com.oauth.oauthstudy.config.auth.PrincipalDetails;
import com.oauth.oauthstudy.domain.member.Member;
import com.oauth.oauthstudy.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


public class JwtAuthenticationProcessingFilter extends BasicAuthenticationFilter {

    private static final String[] NO_CHECK_URL = {"/login", "/"}; // "/login"으로 들어오는 요청은 Filter 작동 X


    private final JwtService jwtService;


    private final MemberRepository memberRepository;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public JwtAuthenticationProcessingFilter(AuthenticationManager authenticationManager, JwtService jwtService, MemberRepository memberRepository) {
        super(authenticationManager);
        this.jwtService = jwtService;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWT필터======================================");

        for (String noCheck : NO_CHECK_URL){
            if(request.getRequestURI().equals(noCheck)){
                filterChain.doFilter(request, response);
                return;
            }
        }

        String refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .orElse(null);

        System.out.println("JWT필터, refreshToken : "+refreshToken);

        if(refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        //if(refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);
        //}
    }


    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        memberRepository.findByRefreshToken(refreshToken)
                .ifPresent(member -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(member);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(member.getUsername()),
                            reIssuedRefreshToken);
                });
    }

    private String reIssueRefreshToken(Member member) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        member.updateRefreshToken(reIssuedRefreshToken);
        memberRepository.saveAndFlush(member);
        return reIssuedRefreshToken;
    }


    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("checkAccessTokenAndAuthentication() 호출");
        System.out.println("jwtService.extractAccessToken(request)"+jwtService.extractAccessToken(request));

        Optional<String> token  = jwtService.extractAccessToken(request);
        if(token.isEmpty()){
            throw new RuntimeException("액세스토큰이 비어있습니다.");
        }
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(username -> memberRepository.findByusername(username)
                                    .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(Member member) {

        String password = member.getPassword();

        PrincipalDetails principalDetails = new PrincipalDetails(member);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null, authoritiesMapper.mapAuthorities(principalDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        }
}