package jpashop.jpabook.domain;

import jpashop.jpabook.domain.item.Item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Category {

    @Id @GeneratedValue
    @Column(name="catergory_id")
    private Long id;

    private String name;

    private List<Item> items;


}
