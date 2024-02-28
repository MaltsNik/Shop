package com.nikita.shop.service.impl;

import com.nikita.shop.entity.Order;
import com.nikita.shop.entity.User;
import com.nikita.shop.model.CreateOrderDto;
import com.nikita.shop.repository.OrderRepository;
import com.nikita.shop.repository.UserRepository;
import com.nikita.shop.service.OrderService;
import com.nikita.shop.service.mapper.OrderMapper;
import com.nikita.shop.model.GetOrderDto;
import com.nikita.shop.model.UpdateOrderDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    @Override
    public GetOrderDto getById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.orElseThrow(() -> new EntityNotFoundException("no order found" + id));
        if (order.isDeletedOrder()) {
            throw new EntityNotFoundException("no order found");
        } else
            return orderMapper.toDto(order);
    }

    @Override
    public GetOrderDto getByCustomerFullName(String fullname) {
        return orderRepository.findByCustomerFullNameContaining(fullname).
                map(orderMapper::toDto).orElseThrow(() -> new EntityNotFoundException("no order found" + fullname));
    }

    @Transactional
    @Override
    public CreateOrderDto add(CreateOrderDto orderDto) {
        if (orderDto.getTotalCost() == null || orderDto.getTotalCost().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("TotalCost must not be negative " + orderDto.getTotalCost());
        }
        User user = userRepository.findById(orderDto.getUserId()).
                orElseThrow(() -> new EntityNotFoundException("No user found" + orderDto.getUserId()));
        Order order = orderMapper.toEntity(orderDto);
        order.setUser(user);
        return orderMapper.toCreatedOrderDto(orderRepository.save(order));
    }

    @Transactional
    @Override
    public void change(UpdateOrderDto orderDto) {
        if (orderDto.getId() == null) {
            throw new IllegalArgumentException("no order found");
        }
        Optional<Order> optionalOrder = orderRepository.findById(orderDto.getId());
        var order = optionalOrder.orElseThrow(() ->
                new EntityNotFoundException("no order found" + orderDto.getId()));
        order.setTotalCost(orderDto.getTotalCost());
        order.setCustomerFullName(orderDto.getCustomerFullName());
        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void removeById(Long id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()) {
            return;
        }
        var order = byId.get();
        order.setDeletedOrder(true);
        orderRepository.save(order);
    }
}