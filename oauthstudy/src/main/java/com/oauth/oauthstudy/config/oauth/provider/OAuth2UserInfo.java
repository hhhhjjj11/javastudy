package com.oauth.oauthstudy.config.oauth.provider;

import com.oauth.oauthstudy.domain.member.Role;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

}
