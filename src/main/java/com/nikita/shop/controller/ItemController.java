package com.nikita.shop.controller;

import com.nikita.shop.model.ItemDto;
import com.nikita.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<List<ItemDto>> getAll() {
        List<ItemDto> items = itemService.getAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getById(@PathVariable Long itemId) {
        ResponseEntity<ItemDto> responseEntity = ResponseEntity.ok(itemService.getById(itemId));
        return responseEntity;
    }

    @PostMapping("/")
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto dto) {
        return ResponseEntity.status(CREATED).body(dto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.remove(itemId);
        return ResponseEntity.ok().build();
    }
}