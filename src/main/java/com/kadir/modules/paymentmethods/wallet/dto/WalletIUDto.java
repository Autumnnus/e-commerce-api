package com.kadir.modules.paymentmethods.wallet.dto;

import com.kadir.common.dto.BaseDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletIUDto extends BaseDto {

    @NotNull(message = "Amount is required")
    private BigDecimal balance;
}
