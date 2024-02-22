package com.nikita.shop.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GetOrderDto {
    private String customerFullName;
    private BigDecimal totalCost;
}