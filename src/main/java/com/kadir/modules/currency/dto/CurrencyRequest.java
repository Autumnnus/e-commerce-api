package com.kadir.modules.currency.dto;

import com.kadir.common.enums.CurrencyCode;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CurrencyRequest {

    @NotNull(message = "CurrencyCode code is required")
    private String currencyCode;

    private String targetCurrencyCode = CurrencyCode.USD.name();
}
