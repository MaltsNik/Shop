package com.nikita.shop.service.impl;

import com.nikita.shop.entity.User;
import com.nikita.shop.repository.UserRepository;
import com.nikita.shop.service.UserService;
import com.nikita.shop.service.mapper.UserMapper;
import com.nikita.shop.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("No user found"));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public Long add(UserDto userDto) {
        return userRepository.save(userMapper.toEntity(userDto)).getId();
    }

    @Transactional
    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}