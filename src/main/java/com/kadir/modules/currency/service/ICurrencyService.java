package com.kadir.modules.currency.service;

import com.kadir.modules.currency.dto.CurrencyDto;
import com.kadir.modules.currency.dto.CurrencyRequest;

import java.util.List;

public interface ICurrencyService {

    List<CurrencyDto> getAllCurrencies();

    CurrencyDto getCurrency(CurrencyRequest request);

    List<CurrencyDto> updateCurrencies();
}
