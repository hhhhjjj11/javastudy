package practice.practice.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class item {
    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;



}
