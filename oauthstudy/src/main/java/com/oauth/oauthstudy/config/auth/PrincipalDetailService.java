package com.oauth.oauthstudy.config.auth;

import com.oauth.oauthstudy.domain.member.Member;
import com.oauth.oauthstudy.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login") 으로 걸어놨기 때문에
// /login 요청이 오면 자동으로 UserDetailService 타입으로 IoC되어 있는 loadByUsername 함수가 실행됨.
@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByusername(username);
        if(memberEntity != null){
            return new PrincipalDetails(memberEntity);
        }
        return null;
    }
}
