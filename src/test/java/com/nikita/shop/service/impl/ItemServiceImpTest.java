package com.nikita.shop.service.impl;

import com.nikita.shop.entity.Item;
import com.nikita.shop.model.ItemDto;
import com.nikita.shop.repository.ItemRepository;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImpTest {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemServiceImpl itemService;
    static ItemDto itemDto;
    static Item item;

    @BeforeAll
    static void init() {
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
    }

    @Test
    void getAll() {
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));

        List<ItemDto> result = itemService.getAll();

        verify(itemRepository).findAll();

        assertEquals(Collections.singletonList(itemDto), result);
    }

    @Test
    void testGetById() {
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));


        ItemDto expected = itemService.getById(0L);

        verify(itemRepository).findById(0L);

        assertEquals(expected, itemDto);
    }

    @Test
    void testAddItem() {
        when(itemRepository.save(item)).thenReturn(item);

        ItemDto expected = itemService.add(itemDto);

        verify(itemRepository).save(item);

        assertEquals(expected, itemDto);
    }

    @Test
    void testDeleteById() {
        itemService.remove(0L);
        verify(itemRepository).deleteById(0L);
    }
}