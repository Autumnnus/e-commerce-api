package com.kadir.modules.currency.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.common.enums.CurrencyCode;
import lombok.Data;

@Data
public class CurrencyDto extends BaseDto {

    private CurrencyCode currencyCode;

    private String currencyName;

    private double exchangeRate;

    private String symbol;

    private Boolean isDefault = false;
}
