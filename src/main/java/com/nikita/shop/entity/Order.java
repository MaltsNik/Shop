package com.nikita.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_fullname")
    private String customerFullName;
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    @Column(name = "deleted_order")
    private boolean deletedOrder;
    @Column(name = "created_by_user_id")
    private Long createdByUserId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "order",
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Item> items;
}