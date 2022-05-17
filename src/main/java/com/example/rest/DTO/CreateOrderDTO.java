package com.example.rest.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderDTO {
    private List<UUID> goodsIds = new ArrayList<>();
    private String withDelivery = null;

}
