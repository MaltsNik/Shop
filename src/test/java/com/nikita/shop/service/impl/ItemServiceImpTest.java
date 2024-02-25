package com.nikita.shop.service.impl;

import com.nikita.shop.entity.Item;
import com.nikita.shop.entity.Order;
import com.nikita.shop.model.ItemDto;
import com.nikita.shop.repository.ItemRepository;
import com.nikita.shop.repository.OrderRepository;
import com.nikita.shop.service.mapper.ItemMapper;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImpTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemMapper itemMapper;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private ItemServiceImpl itemService;
    static ItemDto itemDto;
    static Item item;
    static Order order;

    @BeforeAll
    static void init() {
        order = new Order();
        order.setId(0L);
        order.setCustomerFullName("Test");
        order.setTotalCost(new BigDecimal(0));
        order.setDeleteOrder(false);
        order.setCreatedByUserId(0L);


        itemDto = new ItemDto();
        itemDto.setProductId(0L);
        itemDto.setProductName("Test");
        itemDto.setDescription("Test");
        itemDto.setPrice(new BigDecimal(0));

        item = new Item();
        item.setId(0L);
        item.setProductId(0L);
        item.setProductName("Test");
        item.setDescription("Test");
        item.setPrice(new BigDecimal(0));
        item.setOrder(order);
    }

    @Test
    void getAll() {
        List<Item> entityItemList = Collections.singletonList(item);
        List<ItemDto> itemDtoList = Collections.singletonList(itemDto);

        when(itemRepository.findAll()).thenReturn(entityItemList);
        //when(itemMapper.toDto((Item) entityItemList)).thenReturn((ItemDto) itemDtoList);

        List<ItemDto> result = itemService.getAll();

        verify(itemRepository).findAll();
        verify(itemMapper).toDto((Item) entityItemList);

        assertEquals(result, itemDtoList);
    }

    @Test
    void testGetById() {
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));
        when(itemMapper.toDto(item)).thenReturn(itemDto);

        ItemDto expected = itemService.getById(0L);

        verify(itemRepository).findById(0L);
        verify(itemMapper).toDto(item);

        assertEquals(expected, itemDto);
    }

    @Test
    void testAddItem() {
        when(itemRepository.save(item)).thenReturn(item);
        when(itemMapper.toEntity(itemDto)).thenReturn(item);

        ItemDto expected = itemService.add(itemDto);

        verify(itemRepository).save(item);
        verify(itemMapper).toEntity(itemDto);

        assertEquals(expected, itemDto);
    }

    @Test
    void testDeleteById() {
        itemService.remove(0L);
        verify(itemRepository).deleteById(0L);
    }
}