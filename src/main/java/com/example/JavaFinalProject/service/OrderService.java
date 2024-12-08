package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.model.*;
import com.example.JavaFinalProject.enums.OrderStatus;
import com.example.JavaFinalProject.repository.OrderRepository;
import com.example.JavaFinalProject.repository.ProductRepository;
import com.example.JavaFinalProject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.JavaFinalProject.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;

    // Creează o comandă dintr-un coș de cumpărături
    public Order createOrderFromCart(Long userId) {
        // Obține coșul utilizatorului
        Cart cart = (Cart) cartService.getCartByUser(userId);

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Coșul de cumpărături este gol sau nu există pentru utilizatorul cu ID-ul: " + userId);
        }

        // Creează o listă de OrderItem din CartItem
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> new OrderItem(cartItem.getId(),cartItem.getProduct(), cartItem.getQuantity()))
                .collect(Collectors.toList());

        // Creează o comandă
        Order order = new Order(orderItems, OrderStatus.PENDING, LocalDate.now(), cart.getUser());

        // Calculează suma totală
        order.calculateTotalAmount();

        // Salvează comanda
        return orderRepository.save(order);
    }

    // Returnează toate comenzile
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Returnează comenzile unui utilizator specific
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }


    public Order placeOrder(Long userId, Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Coșul de cumpărături este gol!");
        }

        // Găsește utilizatorul
        Optional<Object> userOpt = userRepository.findById(userId);
        Order order = new Order();

        if (userOpt.isPresent()) {
            User user = (User) userOpt.get();
            order.setUser(user);
            order.setStatus(OrderStatus.PENDING);
            order.setOrderDate(LocalDate.now());

            // Creează OrderItems din cart
            List<OrderItem> orderItems = cart.getItems().stream()
                    .map(cartItem -> {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrder(order);
                        orderItem.setQuantity(cartItem.getQuantity());
                        orderItem.setProduct(cartItem.getProduct());

                        return orderItem;
                    }).collect(Collectors.toList());

            order.setItems(orderItems);
            order.calculateTotalAmount();

            // Actualizează stocurile produselor
            orderItems.forEach(OrderItem::updateProductStock);
        } else {
            throw new EntityNotFoundException("User not found!");
        }

        return orderRepository.save(order);
    }



    // Anulează comanda și returnează stocurile înapoi
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));

        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        });

        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}