package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.dtos.CreateReviewDto;
import com.example.JavaFinalProject.model.Review;

public interface ReviewServiceInterface {

    Review addReview(CreateReviewDto dto);

}
