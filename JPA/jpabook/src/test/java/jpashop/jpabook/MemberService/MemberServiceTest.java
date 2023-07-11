package jpashop.jpabook.MemberService;

import jpashop.jpabook.domain.Member;
import jpashop.jpabook.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    // 회원가입 테스트와 중복회원 예외가 제대로 작동하는지 테스트 고고

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given  "이런게 주어졌을때"
        Member member = new Member();
        member.setName("Kim");
        
        //when   "이렇게 하면"
        Long saveId = memberService.join(member);

        //then   "이렇게 된다"
        assertEquals(member, memberRepository.findOne(saveId));
    }


}