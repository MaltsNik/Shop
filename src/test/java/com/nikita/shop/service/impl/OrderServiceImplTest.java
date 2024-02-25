package com.nikita.shop.service.impl;

import com.nikita.shop.entity.Order;
import com.nikita.shop.entity.User;
import com.nikita.shop.model.*;
import com.nikita.shop.repository.OrderRepository;
import com.nikita.shop.repository.UserRepository;
import com.nikita.shop.service.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;
    static CreateOrderDto createOrderDto;
    static UpdateOrderDto updateOrderDto;
    static GetOrderDto getOrderDto;
    static Order order;
    static UserDto userDto;
    static User user;

    @BeforeAll
    static void init() {
        createOrderDto = new CreateOrderDto();
        createOrderDto.setCreatedByUserId(0L);
        createOrderDto.setCustomerFullName("Test");
        createOrderDto.setTotalCost(new BigDecimal(0));

        updateOrderDto = new UpdateOrderDto();
        updateOrderDto.setId(0L);
        updateOrderDto.setCustomerFullName("Test");
        updateOrderDto.setTotalCost(new BigDecimal(0));

        getOrderDto = new GetOrderDto();
        getOrderDto.setCustomerFullName("Test");
        getOrderDto.setTotalCost(new BigDecimal(0));

        order = new Order();
        order.setId(0L);
        order.setCustomerFullName("Test");
        order.setTotalCost(new BigDecimal(0));
        order.setDeleteOrder(false);
        order.setCreatedByUserId(0L);

        userDto = new UserDto();
        userDto.setName("Test");
        userDto.setEmail("Test");

        user = new User();
        user.setId(0L);
        user.setName("Test");
        user.setEmail("Test");

        order.setUser(user);
    }

    @Test
    void testGetById() {
        when(orderRepository.findById(0L)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(getOrderDto);

        GetOrderDto expected = orderService.getById(0L);

        verify(orderRepository).findById(0L);
        verify(orderMapper).toDto(order);

        assertEquals(expected, getOrderDto);
    }

    @Test
    void testGetByCustomerFullname() {
        when(orderRepository.findByCustomerFullNameContaining("Test")).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(getOrderDto);

        Optional<Order> expected = orderRepository.findByCustomerFullNameContaining("Test");

        verify(orderRepository).findByCustomerFullNameContaining("Test");

        assertTrue(expected.isPresent());
        assertEquals(getOrderDto, orderMapper.toDto(expected.get()));
    }

    @Test
    void testAddOrder() {
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toEntity(createOrderDto)).thenReturn(order);

        CreateOrderDto expected = orderService.add(createOrderDto);

        verify(orderRepository).save(order);
        verify(orderMapper).toEntity(createOrderDto);

        assertEquals(expected, createOrderDto);
    }

    @Test
    void testChangeOrder() {
        when(orderRepository.findById(0L)).thenReturn(Optional.of(order));

        orderService.change(0L, updateOrderDto);

        verify(orderRepository).save(order);
    }

//    @Test
//    void testShouldRemoveOrderById() {
////        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
////
////        orderService.removeById(order.getId());
////
////        verify(orderRepository).findById(order.getId());
////        verify(orderRepository).save(order);
//
//        orderService.removeById(0L);
//        //verify(orderRepository).deleteById(0L);
//        //verify(order).setDeleteOrder(true);
//
//    }

    @Test
    void testShouldNotRemoveOrderIfNotFound() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.empty());

        orderService.removeById(order.getId());

        verify(orderRepository).findById(order.getId());
        verifyNoMoreInteractions(orderRepository);
    }
}