package com.example.JavaFinalProject.controller;

import com.example.JavaFinalProject.dtos.RequestOrderDto;
import com.example.JavaFinalProject.model.Cart;
import com.example.JavaFinalProject.model.Order;
import com.example.JavaFinalProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody RequestOrderDto orderDto) {
        System.out.println("Sunt in placeOrder");
        Order order = orderService.placeOrder(orderDto.getUserId(), (Cart) orderDto.getItems());
        return ResponseEntity.ok(order);
    }

    @PutMapping
    public ResponseEntity<Order> cancelOrder(@RequestParam Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
