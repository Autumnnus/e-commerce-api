package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoCartItemsIU {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Product id is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

}
