package jpashop.jpabook.MemberService;

import jpashop.jpabook.domain.Member;
import jpashop.jpabook.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest   // 스프링 부트를 띄운 상태로 테스트를 하려면 이게 필요함. 없으면 autowired다 실패함.
@Transactional      //
public class MemberServiceTest {

    // 회원가입 테스트와 중복회원 예외가 제대로 작동하는지 테스트 고고

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception {
        //given  "이런게 주어졌을때"
        Member member = new Member();
        member.setName("Kim");
        
        //when   "이렇게 하면"
        Long saveId = memberService.join(member);

        //then   "이렇게 된다"
        assertEquals(member, memberRepository.findOne(saveId));
        // 인자 두 개가 서로 같다. -> 그러면 통과
    }

    @Test(expected = IllegalStateException.class)   // 메서드 본문 실행 중 해당 에러가 발생하면 통과가 되도록 하는 설정임.
    public void 중복_회원_예외() throws Exception {
        //given "이렇게 이름이 같은 회원이 주어져있을 때"
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when "둘 다 회원가입을 하려고하면"
        memberService.join(member1);
        memberService.join(member2);  // 예외가 발생해야 한다!!

        //then "에러가 나야 한다.."
        fail("예외가 발생해야 한다."); // 기능 : 여기까지 오면 실패. (즉, 위에서 에러처리가 안되었기때문에 여기까지 온거니까.)
    }
}