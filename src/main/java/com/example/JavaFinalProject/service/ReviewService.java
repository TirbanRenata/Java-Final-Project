package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.dtos.CreateReviewDto;
import com.example.JavaFinalProject.model.Product;
import com.example.JavaFinalProject.model.Review;
import com.example.JavaFinalProject.model.User;
import com.example.JavaFinalProject.repository.ProductRepository;
import com.example.JavaFinalProject.repository.ReviewRepository;
;
import com.example.JavaFinalProject.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService implements ReviewServiceInterface{
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserJpaRepository userRepository;


    public Review addReview(CreateReviewDto dto) {
        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        review.setReviewDate(LocalDateTime.now());

        User user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("User was not found!"));
        review.setUser(user);

        Product product = productRepository.findById(dto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("Product not found!"));
        review.setProduct(product);

        return reviewRepository.save(review);
    }

    // Obține recenziile pentru un produs
    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}
