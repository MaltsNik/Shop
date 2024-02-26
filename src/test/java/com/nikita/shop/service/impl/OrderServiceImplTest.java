package com.nikita.shop.service.impl;

import com.nikita.shop.entity.Order;
import com.nikita.shop.model.*;
import com.nikita.shop.repository.OrderRepository;
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
    @InjectMocks
    private OrderServiceImpl orderService;
    static CreateOrderDto createOrderDto;
    static UpdateOrderDto updateOrderDto;
    static GetOrderDto getOrderDto;
    static Order order;

    @BeforeAll
    static void init() {
        createOrderDto = new CreateOrderDto();
        createOrderDto.setUserId(0L);
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
        order.setDeletedOrder(false);
        order.setCreatedByUserId(0L);
    }

    @Test
    void testGetById() {
        when(orderRepository.findById(0L)).thenReturn(Optional.of(order));


        GetOrderDto expected = orderService.getById(0L);

        verify(orderRepository).findById(0L);


        assertEquals(expected, getOrderDto);
    }

    @Test
    void testGetByCustomerFullname() {
        when(orderRepository.findByCustomerFullNameContaining("Test")).thenReturn(Optional.of(order));


        Optional<Order> expected = orderRepository.findByCustomerFullNameContaining("Test");

        verify(orderRepository).findByCustomerFullNameContaining("Test");

        assertTrue(expected.isPresent());
        assertEquals(getOrderDto, expected.get());
    }

    @Test
    void testAddOrder() {
        when(orderRepository.save(order)).thenReturn(order);


        CreateOrderDto expected = orderService.add(createOrderDto);

        verify(orderRepository).save(order);

        assertEquals(expected, createOrderDto);
    }

    @Test
    void testChangeOrder() {
        when(orderRepository.findById(0L)).thenReturn(Optional.of(order));

        //orderService.change(0L, updateOrderDto);

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