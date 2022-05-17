package com.example.rest.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "goods")
@Getter
@Setter
@ToString
public class GoodsEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "good_type")
    private String goodsType = "";
    private int discount = 0;
    private int price;
    @Column(name = "current_price")
    private int currentPrice;
    private String name = "";
    private String description = "";
    private String img = "";

    @ManyToMany(mappedBy = "goods", fetch = FetchType.LAZY)
    private Set<OrderEntity> orders = new HashSet<>();

    public void addOrder(OrderEntity order) {
        this.orders.add(order);
    }
}
