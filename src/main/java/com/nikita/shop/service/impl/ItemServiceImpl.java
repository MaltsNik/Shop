package com.nikita.shop.service.impl;

import com.nikita.shop.entity.Item;
import com.nikita.shop.entity.Order;
import com.nikita.shop.model.ItemDto;
import com.nikita.shop.repository.ItemRepository;
import com.nikita.shop.repository.OrderRepository;
import com.nikita.shop.service.ItemService;
import com.nikita.shop.service.mapper.ItemMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemDto> getAll() {
        Optional<List<Item>> optionalItems = Optional.of(itemRepository.findAll());
        List<Item> items = optionalItems.orElseThrow(() -> new EntityNotFoundException("no item found"));
        return Collections.singletonList(itemMapper.toDto((Item) items));
    }

    @Override
    public ItemDto getById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        Item item = optionalItem.orElseThrow(() -> new EntityNotFoundException("no item found"));
        return itemMapper.toDto(item);
    }

    @Transactional
    @Override
    public ItemDto add(ItemDto dto) {
        Order order = orderRepository.findById(dto.getProductId()).
                orElseThrow(() -> new EntityNotFoundException("no item found"));
        Item item = itemMapper.toEntity(dto);
        item.setOrder(order);
        return itemMapper.toDto(itemRepository.save(item));
    }

    @Transactional
    @Override
    public void remove(Long id) {
        itemRepository.deleteById(id);
    }
}