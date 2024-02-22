package com.nikita.shop.service;

import com.nikita.shop.model.UserDto;

public interface UserService {
    UserDto getById(Long id);

    Long add(UserDto userDto);

    void removeById(Long id);
}