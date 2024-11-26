package com.kadir.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DtoOrderIU {

    @NotNull(message = "User id is required")
    private Long userId;

    private String paymentMethod;

    private String paymentStatus;
}
