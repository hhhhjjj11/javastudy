package jpashop.jpabook.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private List<OrderItem> orterItems = new ArrayList<>();

    private Delivery delivery;

    private LocalDateTime orderDate;

    private OrderStatus status; // 주문상태 [Order, Cancel]

}
