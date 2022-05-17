package com.example.rest.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterGoodsDTO {
    private String name = "";
    private int minPrice;
    private int maxPrice;
}
