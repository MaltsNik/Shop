package com.nikita.shop.service.mapper;


import com.nikita.shop.entity.Order;
import com.nikita.shop.model.CreateOrderDto;
import com.nikita.shop.model.GetOrderDto;
import com.nikita.shop.model.UpdateOrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    GetOrderDto toDto(Order order);

    Order toEntity(CreateOrderDto dto);
    CreateOrderDto toCreatedOrderDto(Order order);

    Order fromSaveOrderDto(UpdateOrderDto dto);
}