package com.oauth.oauthstudy.repository;

import com.oauth.oauthstudy.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

// CRUD 를 JpaRepository가 들고있음
// @Repository 생략 가능
public interface MemberRepository extends JpaRepository<Member, Integer> {


    Optional<Member> findByusername(String username);

    Optional<Member> findByRefreshToken(String refreshToken);
}
