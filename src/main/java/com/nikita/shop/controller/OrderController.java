package com.nikita.shop.controller;

import com.nikita.shop.model.CreateOrderDto;
import com.nikita.shop.repository.UserRepository;
import com.nikita.shop.service.OrderService;
import com.nikita.shop.model.GetOrderDto;
import com.nikita.shop.model.UpdateOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/shop")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;

    @GetMapping("/orders/{id}")
    public ResponseEntity<GetOrderDto> getOrderById(@PathVariable Long id) {
        var orderDto = orderService.getById(id); // добавить exception handler на 404 not found
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/orders/fullname")
    public ResponseEntity<GetOrderDto> getOrderByCustomerFullname(@RequestParam String fullname) {
        return ResponseEntity.ok(orderService.getByCustomerFullName(fullname));
    }

    @PostMapping("/orders")
    public ResponseEntity<CreateOrderDto> addOrder(@RequestBody CreateOrderDto dto) {
        return ResponseEntity.ok(orderService.add(dto));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateOrderDto dto) {
        orderService.change(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.removeById(id);
        return ResponseEntity.ok().build();
    }
}