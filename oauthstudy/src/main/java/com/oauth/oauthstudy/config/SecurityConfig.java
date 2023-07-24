package com.oauth.oauthstudy.config;

import com.oauth.oauthstudy.Service.JwtService;
import com.oauth.oauthstudy.config.filter.JwtAuthenticationProcessingFilter;
import com.oauth.oauthstudy.config.oauth.PrincipalOauth2UserService;
import com.oauth.oauthstudy.config.oauth.handler.OAuth2LoginSuccessHandler;
import com.oauth.oauthstudy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration // IoC 빈(bean)을 등록
@RequiredArgsConstructor
public class SecurityConfig {


    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MemberRepository memberRepository;


    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or
                // hasRole('ROLE_USER')")
                // .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and
                // hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .successHandler(oAuth2LoginSuccessHandler)
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

        http.addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
        return jwtAuthenticationFilter;
    }

}