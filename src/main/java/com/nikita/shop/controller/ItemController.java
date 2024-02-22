package com.nikita.shop.controller;

import com.nikita.shop.model.ItemDto;
import com.nikita.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<ItemDto> items = itemService.getAll();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @PostMapping("/items")
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto dto) {
        return ResponseEntity.ok(itemService.add(dto));
    }

    @DeleteMapping("/items{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.remove(id);
        return ResponseEntity.ok().build();
    }
}
