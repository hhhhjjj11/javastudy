package com.oauth.oauthstudy.config.auth;

import com.oauth.oauthstudy.domain.member.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;     // 이거는 카카오서버로부터 받은 유저정보를 담기 위해 만든 멤버변수임
                                                // 카카오서버에서 accesstoken과 함께 유저정보를 주는 attributes객체를 함께 넘겨줌
                                                // OAuth로그인시 Authentication객체에 넣을 User객체를 만들때 attributes를 담아줌.

    // 일반 로그인
    public PrincipalDetails(Member member){
        this.member = member;
    }

    // OAuth 로그인
    public PrincipalDetails(Member member, Map<String, Object> attributes){

        this.member = member;
        this.attributes = attributes;
    }


    @Override
    public Map<String, Object> getAttributes()
    {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return null;
    }


    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
