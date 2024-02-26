package com.nikita.shop.service;

import com.nikita.shop.model.CreateOrderDto;
import com.nikita.shop.model.GetOrderDto;
import com.nikita.shop.model.UpdateOrderDto;

import java.util.List;

public interface OrderService {
    GetOrderDto getById(Long id);

    GetOrderDto getByCustomerFullName(String fullname);

    CreateOrderDto add(CreateOrderDto orderDto);

    void change( UpdateOrderDto dto);

    void removeById(Long id);
}