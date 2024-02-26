package com.nikita.shop.controller;

import com.nikita.shop.model.CreateOrderDto;
import com.nikita.shop.service.OrderService;
import com.nikita.shop.model.GetOrderDto;
import com.nikita.shop.model.UpdateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderDto> getOrderById(@PathVariable Long id) {
        var orderDto = orderService.getById(id);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/fullname")
    public ResponseEntity<GetOrderDto> getOrderByCustomerFullname(@RequestParam String fullname) {
        return ResponseEntity.ok(orderService.getByCustomerFullName(fullname));
    }

    @PostMapping("/")
    public ResponseEntity<CreateOrderDto> addOrder(@RequestBody CreateOrderDto dto) {
        orderService.add(dto);
        return ResponseEntity.status(CREATED).body(dto);
    }

    @PutMapping("/")
    public ResponseEntity<?> update( @RequestBody UpdateOrderDto dto) {
        orderService.change(dto);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.removeById(id);
        return ResponseEntity.ok().build();
    }
}