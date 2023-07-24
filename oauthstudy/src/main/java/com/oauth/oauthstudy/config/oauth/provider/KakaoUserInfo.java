package com.oauth.oauthstudy.config.oauth.provider;

import com.oauth.oauthstudy.domain.member.Role;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; // getAttributes()

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes=attributes;
    }
    @Override
    public String getProviderId() {
        return (String) attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "Kakao";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return null;
    }

}
