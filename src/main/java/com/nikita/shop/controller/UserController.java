package com.nikita.shop.controller;

import com.nikita.shop.model.UserDto;
import com.nikita.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
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

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        ResponseEntity<UserDto> responseEntity = ResponseEntity.ok(userService.getByName(name));
        return responseEntity;
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addUser(@RequestBody UserDto userDto) {
        userService.add(userDto);
        log.info("юзер сохраняется" + userDto.toString());
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.removeById(userId);
        return ResponseEntity.ok().build();
    }
}