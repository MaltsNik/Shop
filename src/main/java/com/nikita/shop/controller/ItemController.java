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

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto dto) {
        return ResponseEntity.status(CREATED).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.remove(id);
        return ResponseEntity.ok().build();
    }
}
