package com.kadir.modules.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDtoIU {

    @NotNull(message = "User id is required")
    private Long userId;

    private String paymentMethod;

    private String paymentStatus;
}
