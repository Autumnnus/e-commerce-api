package com.kadir.modules.paymentmethods.creditcard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardUpdateDto {

    @NotNull(message = "Address id is required")
    private Long addressId;
}
