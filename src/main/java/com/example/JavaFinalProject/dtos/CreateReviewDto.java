package com.example.JavaFinalProject.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateReviewDto {
    private Long userId;
    private Long productId;
    private Integer rating;
    private String comment;
}

