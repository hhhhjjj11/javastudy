package practice.practice;

import javax.persistence.*;

@Entity
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    private int count;

    private int orderPrice;

}