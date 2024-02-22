package com.nikita.shop.service;

import com.nikita.shop.model.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getAll();

    ItemDto getById(Long id);

    ItemDto add(ItemDto dto);

    void remove(Long id);
}