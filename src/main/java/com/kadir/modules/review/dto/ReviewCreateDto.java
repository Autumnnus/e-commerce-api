package com.kadir.modules.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateDto {

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private int rating;

    @NotNull(message = "Comment cannot be null")
    private String comment;
}
