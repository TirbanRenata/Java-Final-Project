package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.model.Order;
import com.example.JavaFinalProject.model.OrderItem;

import java.util.List;

public interface OrderServiceInterface {

    Order placeOrder(Long userId, List<OrderItem> items);
    Order cancelOrder(Long orderId);

}
