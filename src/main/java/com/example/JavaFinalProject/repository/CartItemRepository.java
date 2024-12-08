package com.example.JavaFinalProject.repository;

import com.example.JavaFinalProject.model.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
}
