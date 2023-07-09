package jpashop.jpabook;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue   // 해당 필드를 식별자로 설정, db에서 자동으로 값 생성
    private long id;

    private String username;
}
