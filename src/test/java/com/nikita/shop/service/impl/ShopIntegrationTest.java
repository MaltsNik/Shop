package com.nikita.shop.service.impl;

import com.nikita.shop.model.UserDto;
import com.nikita.shop.repository.ItemRepository;
import com.nikita.shop.repository.OrderRepository;
import com.nikita.shop.repository.UserRepository;
import com.nikita.shop.service.ItemService;
import com.nikita.shop.service.OrderService;
import com.nikita.shop.service.UserService;
import com.nikita.shop.service.mapper.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Properties;

@Testcontainers
public class ShopIntegrationTest {
    @Container
    private static final PostgreSQLContainer container = new PostgreSQLContainer<>("postgres");
    private static OrderRepository orderRepository;
    private static OrderService orderService;
    private static OrderMapper orderMapper;
    private static ItemRepository itemRepository;
    private static ItemService itemService;
    private static ItemMapper itemMapper;
    private static UserRepository userRepository;
    private static UserService userService;
    private static UserMapper userMapper;

    @BeforeAll
    static void init() {
        container.start();
        Properties properties = new Properties();
        properties.put("hibernate.connection.url", container.getJdbcUrl());
        properties.put("hibernate.connection.username", container.getUsername());
        properties.put("hibernate.connection.password", container.getPassword());
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        orderMapper = new OrderMapperImpl();
        orderService = new OrderServiceImpl(orderRepository, orderMapper, userRepository);
        itemMapper = new ItemMapperImpl();
        itemService = new ItemServiceImpl(itemRepository, orderRepository, itemMapper);
        userMapper = new UserMapperImpl();
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void integrationGetAllTest() {
        UserDto userDto = new UserDto();
        userDto.setName("Test");
        userDto.setEmail("Test");

        Long addUser = userService.add(userDto);
        System.out.println(addUser);

        UserDto expected = userService.getById(addUser);
        Assertions.assertEquals(expected, userDto);

        userService.removeById(addUser);
        Assertions.assertThrows(RuntimeException.class, () -> userService.getById(addUser));

//        CreateOrderDto orderDto = new CreateOrderDto();
//        orderDto.setCreatedByUserId(0L);
//        orderDto.setCustomerFullName("Test");
//        orderDto.setTotalCost(new BigDecimal(0));
//        CreateOrderDto addOrder = orderService.add(orderDto);
//        CreateOrderDto expectedOrderDto = orderService.getById(addOrder)
    }

}
