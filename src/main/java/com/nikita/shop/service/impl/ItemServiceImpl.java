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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemDto> getAll() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(itemMapper::toDto).collect(Collectors.toList());
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