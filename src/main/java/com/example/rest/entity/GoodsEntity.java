package com.example.rest.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "goods")
@Getter
@Setter
@ToString
public class GoodsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "good_type")
    private String goodsType = "";
    private int discount = 0;
    private int price;
    @Column(name = "current_price")
    private int currentPrice;
    private String name = "";
    private String description = "";
    private String img = "";

    @ManyToMany(mappedBy = "goods", fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<OrderEntity> orders = new HashSet<>();

    public void addOrder(OrderEntity order) {
        this.orders.add(order);
    }
    public void removeOrder(Integer id) {
        this.orders.removeIf((item) -> item.getId().equals(id));
    }


}
