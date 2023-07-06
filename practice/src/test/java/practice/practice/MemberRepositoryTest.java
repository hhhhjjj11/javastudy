package practice.practice;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember(){

        // 테스트 흐름
        // 인스턴스에 이름 설정해서 -> 저장하고 -> 아이디를 얻어서 -> DB에서 찾고 ->
        // 이제 DB에서 꺼낸거랑 초기에 꺼낸거랑 필드 별로 비교해줌

        Member member = new Member();
        member.setUsername("memberA");

        Long saveId = memberRepository.save(member);

        Member findMember = memberRepository.find(saveId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        //이 메서드는 주어진 조건을 검증하고, 조건이 참이 아닐 경우에는 테스트를 실패(fail)로 표시
    }
}