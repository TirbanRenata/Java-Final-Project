package com.example.JavaFinalProject.controller;
import com.example.JavaFinalProject.dtos.CreateReviewDto;
import com.example.JavaFinalProject.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.JavaFinalProject.service.ReviewService;



@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody CreateReviewDto dto) {
        return ResponseEntity.ok(reviewService.addReview(dto));
    }

}

