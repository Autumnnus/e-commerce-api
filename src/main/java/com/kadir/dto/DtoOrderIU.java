package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoOrderIU {

    @NotNull(message = "User id is required")
    private Long userId;

//    @NotNull(message = "Order items are required")
//    private Set<String> orderItems;

    private String paymentMethod;

    private String paymentStatus;
}
