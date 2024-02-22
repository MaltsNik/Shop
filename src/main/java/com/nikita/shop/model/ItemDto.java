package com.nikita.shop.model;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDto {
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
}