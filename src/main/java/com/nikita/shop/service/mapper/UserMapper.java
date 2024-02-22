package com.nikita.shop.service.mapper;

import com.nikita.shop.entity.User;
import com.nikita.shop.model.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}