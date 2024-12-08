package com.example.JavaFinalProject.dtos;

import com.example.JavaFinalProject.enums.ProductCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data


public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private ProductCategory category;

    public ProductDto(Long id, String name, String description, BigDecimal price, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
