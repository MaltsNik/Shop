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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Long> addUser(@RequestBody UserDto userDto) {
        userService.add(userDto);
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return ResponseEntity.ok().build();
    }
}