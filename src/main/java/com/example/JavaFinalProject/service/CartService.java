package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.model.Cart;
import com.example.JavaFinalProject.model.CartItem;
import com.example.JavaFinalProject.model.Product;
import com.example.JavaFinalProject.model.User;
import com.example.JavaFinalProject.repository.CartRepository;
import com.example.JavaFinalProject.repository.CartItemRepository;
import com.example.JavaFinalProject.repository.ProductRepository;
import com.example.JavaFinalProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    // Obține coșul de cumpărături pentru utilizatorul specificat
    public Cart getCartByUser(Long userId) {
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        // Dacă nu există un coș pentru utilizator, creăm unul nou
        if (cart.isPresent()) {
            return cart.get();
        } else {
            User user = (User) userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Cart newCart = new Cart(user, new ArrayList<>());
            cartRepository.save(newCart);
            return newCart;
        }
    }

    // Adaugă un produs în coșul de cumpărături
    public void addProductToCart(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produsul nu există."));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCartForUser(userId));// Crează un coș nou dacă nu există

        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem cartItem = new CartItem(product, quantity, cart);
            cart.getItems().add(cartItem);
            cartRepository.save(cart);
        }
    }

    // Crează un coș nou pentru un utilizator
    private Cart createNewCartForUser(Long userId) {
        // Creăm un utilizator cu un ID (poți adăuga și alte informații dacă ai nevoie)
        User user = new User(userId, "Nume Default", "email@exemplu.com", 30);  // Exemplu cu un ID Long
        Cart cart = new Cart(user, new ArrayList<>()); // Creăm un coș gol pentru utilizator
        cartRepository.save(cart);
        return cart;
    }


    // Actualizează cantitatea unui produs în coș
    public void updateProductQuantity(Long cartItemId, int quantity) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        cartItem.ifPresent(item -> {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        });
    }

    // Elimină un produs din coș
    public void removeProductFromCart(Long cartItemId) {

        cartItemRepository.deleteById(cartItemId);
    }
}
