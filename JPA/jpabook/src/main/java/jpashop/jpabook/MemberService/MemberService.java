package jpashop.jpabook.MemberService;

import jpashop.jpabook.domain.Member;
import jpashop.jpabook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// 조회전용 메서드의 경우 readOnly=true를 하면 성능에 좋기때문에 무조건 해주는게 좋다. 클래스에 걸어버리면 하위의 메서드에 전부 걸린다.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입. 이때 중복회원을 검사하는 메서드를 만들어서 이용한다.
    @Transactional
    public Long join(Member member){

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member){
        // 이상하면 예외를 터뜨려주는 검사메서드
        List<Member> findMembers = memberRepository.findByNames(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() { return memberRepository.findAll();}

    public Member findOne(Long memberId) { return memberRepository.findOne(memberId);}
}
