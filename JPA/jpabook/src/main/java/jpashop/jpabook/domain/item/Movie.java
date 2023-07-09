package jpashop.jpabook.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
public class Movie {

    private String director;
    private String actor;

}
