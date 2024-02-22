package com.nikita.shop.service.mapper;

import com.nikita.shop.entity.Item;
import com.nikita.shop.model.ItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toDto(Item item);

    Item toEntity(ItemDto itemDto);
}