package com.example.JavaFinalProject.repository;

import com.example.JavaFinalProject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findAllByOrderDateBetween(LocalDate startDate, LocalDate endDate);

}
