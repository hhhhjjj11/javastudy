package com.oauth.oauthstudy.config.oauth;

import com.oauth.oauthstudy.config.auth.PrincipalDetails;
import com.oauth.oauthstudy.config.oauth.provider.KakaoUserInfo;
import com.oauth.oauthstudy.config.oauth.provider.OAuth2UserInfo;
import com.oauth.oauthstudy.domain.member.Member;
import com.oauth.oauthstudy.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    // @Bean의 기능 - 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private MemberRepository memberRepository;
    // 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: "+userRequest.getClientRegistration());
        System.out.println("getAccessToken.getTokenValue: "+userRequest.getAccessToken().getTokenValue());

        // 카카오서버로부터 받은 요청객체로부터 사용자정보 객체를 추출하는 로직 인듯??
        OAuth2User oAuth2User = super.loadUser(userRequest);  // super : 상위 클래스의 메서드를 불러와서 쓴다 이말인듯
        System.out.println("oAuth2User: "+oAuth2User);
        System.out.println("getAttributes: "+oAuth2User.getAttributes()); // 추출한 user객체로부터 속성들을 뽑아냄
        System.out.println("=================================================================");

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;
        if(registrationId.equals("kakao")){
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }else if (registrationId.equals("google")){
            // 추후 작성 요망.
        }

        // [Provider 인터페이스를 만들기 전]회원가입 자동진행
//        String provider = userRequest.getClientRegistration().getClientId(); // google
//        Long providerId = oAuth2User.getAttribute("id");
//        String username = provider+"_"+providerId;  // kakao_123102401051349
//        String email = oAuth2User.getAttribute("email");
//        String role = "ROLE_USER";

        // [Provider 인터페이스를 만든 후]회원가입 자동진행
        String provider = oAuth2UserInfo.getProvider(); // kakao
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;  // kakao_123102401051349
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";


        Optional<Member> memberOptional = memberRepository.findByusername(username);
        Member memberEntity = memberOptional.orElseGet(() -> {
            return Member.builder()
                    .username(username)
                    .provider(provider)
                    .providerId(providerId)
                    .email(email)
                    .role(role)
                    .build();
        });
        memberOptional.ifPresentOrElse(
                member -> { /* 이미 회원 정보가 데이터베이스에 존재하는 경우 */ },
                () -> memberRepository.save(memberEntity) /* 회원 정보가 데이터베이스에 없는 경우 */
        );

// PrincipalDetails 객체(Userdata객체) 만들어서 리턴.
// 여기서 remind. PrincipalDetails 에서 생성자를 두 개를 만들었었다.
// 하나는 일반로그인용, 하나는 OAuth로그인용
// 둘의 차이는 카카오서버로부터 받은 유저정보인 Attributes 데이터를 인자로 넣어주냐마냐의 차이.
// 여기서는 물론 OAuth 생성자를 사용해서, member객체 뿐만아니라 Attributes까지 넣어준다!!!
        return new PrincipalDetails(memberEntity, oAuth2User.getAttributes());

    }
}
