package com.nikita.shop.service.impl;

import com.nikita.shop.entity.User;
import com.nikita.shop.repository.UserRepository;
import com.nikita.shop.service.UserService;
import com.nikita.shop.service.mapper.UserMapper;
import com.nikita.shop.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserDto getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("No user found" + id));
        return userMapper.toDto(user);
    }

    @Override
    @Cacheable(value = "users", key = "#name")
    public UserDto getByName(String name) {
        Optional<User> optionalUser = userRepository.findByName(name);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("No user found" + name));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    @CacheEvict(value = "users", key = "#userDto")
    public Long add(UserDto userDto) {
        return userRepository.save(userMapper.toEntity(userDto)).getId();
    }

    @Transactional
    @Override
    @CacheEvict(value = "users", key = "#id")
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}