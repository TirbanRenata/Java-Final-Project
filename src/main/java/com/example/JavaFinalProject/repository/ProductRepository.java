package com.example.JavaFinalProject.repository;

import com.example.JavaFinalProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String keyword);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByCategory(String category);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findByStockQuantityGreaterThan(int q);

}

