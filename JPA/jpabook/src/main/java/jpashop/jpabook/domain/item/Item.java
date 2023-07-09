package jpashop.jpabook.domain.item;

import jpashop.jpabook.domain.Category;
import jpashop.jpabook.domain.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name= "dtype")
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderitem;

    private List<Category> category;
}
