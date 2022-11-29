package com.example.rest.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCurierDTO {
    private String name;
    private String phone;
    private String status = "free";
}
