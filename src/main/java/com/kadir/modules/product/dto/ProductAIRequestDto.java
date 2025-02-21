package com.kadir.modules.product.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAIRequestDto {

    @NotNull(message = "Content is required")
    private String content;

}
