package jpashop.jpabook.repository;

import jpashop.jpabook.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    // 회원저장
    // 회원 한명 찾기
    // 회원전체조회
    // 이름으로찾기

    //1. 회원저장
    public void save(Member member){
        em.persist(member);
    }

    //2. 회원 한명 찾기
    public Member findOne(Long id){
        return em.find(Member.class, id);  // 첫번째 인자로 반환타입, 두번째인자로 pk 넣어주면 됨.
    }

    //3. 회원전체조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)  // JPQL문을 넣음. JPQL문법은 SQL과 거의 같긴하지만 from의 대상이 table이 아니라 Entity임
                .getResultList();
    }

    //4. 이름을 가지고 회원 찾기
    public List<Member> findByNames(String name){
        return em.createQuery("select m from Member m where m.name =:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
