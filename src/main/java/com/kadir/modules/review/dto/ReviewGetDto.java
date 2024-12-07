package com.kadir.modules.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewGetDto {

    @NotNull(message = "Product id cannot be null")
    private Long productId;
}
