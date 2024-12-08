package com.example.JavaFinalProject.controller;

import com.example.JavaFinalProject.model.Cart;
import com.example.JavaFinalProject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Obține coșul de cumpărături pentru utilizatorul specificat
    @GetMapping("/{userId}")
    public Cart getCartByUserId(@PathVariable Long userId) {
        // Returnează coșul complet (nu doar CartItem-uri)
        return cartService.getCartByUser(userId);
    }

    // Adaugă un produs în coș
    @PostMapping("/{userId}/add")
    public void addProductToCart(@PathVariable Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(userId, productId, quantity);
    }

    // Elimină un produs din coș
    @DeleteMapping("/remove/{cartItemId}")
    public void removeProductFromCart(@PathVariable Long cartItemId) {
        cartService.removeProductFromCart(cartItemId);
    }
}
