package com.kadir.modules.productviews.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductViewsSaveDto {

    @NotNull(message = "Product id is required")
    public Long productId;

    public Long userId;

    public String ipAddress;
}
