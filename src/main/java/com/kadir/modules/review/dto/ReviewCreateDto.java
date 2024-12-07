package com.kadir.modules.review.dto;

import com.kadir.common.enums.ReviewRate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateDto {

    @NotNull(message = "Product id cannot be null")
    private Long productId;

    @NotNull(message = "Rating cannot be null")
    private ReviewRate rating;

    @NotNull(message = "Comment cannot be null")
    private String comment;
}
