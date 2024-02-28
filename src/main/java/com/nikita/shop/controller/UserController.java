package com.nikita.shop.controller;

import com.nikita.shop.model.UserDto;
import com.nikita.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        ResponseEntity<UserDto> responseEntity = ResponseEntity.ok(userService.getById(userId));
        return responseEntity;
    }

    @PostMapping("/")
    public ResponseEntity<Long> addUser(@RequestBody UserDto userDto) {
        userService.add(userDto);
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.removeById(userId);
        return ResponseEntity.ok().build();
    }
}