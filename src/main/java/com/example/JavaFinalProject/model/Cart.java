package com.example.JavaFinalProject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "carts")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Cart(User user,List<CartItem> items) {
        this.user = user;
        this.items = items;
    }

}
