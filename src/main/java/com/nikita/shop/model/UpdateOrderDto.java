package com.nikita.shop.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateOrderDto {
    private Long id;
    private String customerFullName;
    private BigDecimal totalCost;
}