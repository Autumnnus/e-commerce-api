package com.kadir.modules.cartitems.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemsUpdateDto {

    @NotNull(message = "Quantity is required")
    private Integer quantity;

}
