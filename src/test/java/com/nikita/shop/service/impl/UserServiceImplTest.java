package com.nikita.shop.service.impl;

import com.nikita.shop.entity.User;
import com.nikita.shop.model.UserDto;
import com.nikita.shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    static UserDto userDto;
    static User user;

    @BeforeAll
    static void init() {
        userDto = new UserDto();
        userDto.setName("Test");
        userDto.setEmail("Test");

        user = new User();
        user.setId(0L);
        user.setName("Test");
        user.setEmail("Test");
    }

    @Test
    void testGetById() {
        when(userRepository.findById(0L)).thenReturn(Optional.of(user));

        UserDto expected = userService.getById(0L);

        verify(userRepository).findById(0L);

        assertEquals(expected, userDto);
    }

    @Test
    void testAddUser() {
        when(userRepository.save(user)).thenReturn(user);

        Long expected = userService.add(userDto);

        verify(userRepository).save(user);

        assertEquals(expected, user.getId());
    }

    @Test
    void testRemoveById() {
        userService.removeById(0L);
        verify(userRepository).deleteById(0L);
    }
}