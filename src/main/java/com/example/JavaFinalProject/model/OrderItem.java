package com.example.JavaFinalProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table(name = "order_item")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;


public OrderItem(){}

    public OrderItem(Long id,Product product, int quantity) {
        this.id = id;
        this.product = this.product;
        this.quantity = this.quantity;
    }
    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }


    public void setQuantity(Integer quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive!");

        this.quantity = quantity;
    }


    public void updateProductStock() {
        this.product.setStockQuantity(this.product.getStockQuantity() - this.quantity);

    }
}

