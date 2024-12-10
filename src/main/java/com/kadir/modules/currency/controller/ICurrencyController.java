package com.kadir.modules.currency.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.currency.dto.CurrencyDto;
import com.kadir.modules.currency.dto.CurrencyRequest;

import java.util.List;

public interface ICurrencyController {

    ApiResponse<List<CurrencyDto>> getAllCurrencies();

    ApiResponse<CurrencyDto> getCurrency(CurrencyRequest request);

    ApiResponse<List<CurrencyDto>> updateCurrencies();
}
