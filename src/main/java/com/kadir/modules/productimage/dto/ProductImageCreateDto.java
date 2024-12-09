package com.kadir.modules.productimage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductImageCreateDto {

    private String altText;

    private boolean isPrimary;

    @NotNull(message = "Product Id is required")
    private Long productId;
}
