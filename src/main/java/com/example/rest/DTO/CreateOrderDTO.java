package com.example.rest.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderDTO {
    private List<Integer> goodsIds = new ArrayList<>();
    private String withDelivery = null;

}
