package com.kadir.modules.currency.controller.impl;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.currency.controller.ICurrencyController;
import com.kadir.modules.currency.dto.CurrencyDto;
import com.kadir.modules.currency.dto.CurrencyRequest;
import com.kadir.modules.currency.service.ICurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/api/currency")
public class CurrencyController extends RestBaseController implements ICurrencyController {

    @Autowired
    private ICurrencyService currencyService;

    @GetMapping("/all")
    @Override
    public ApiResponse<List<CurrencyDto>> getAllCurrencies() {
        return ok(currencyService.getAllCurrencies());
    }

    @GetMapping("/get")
    @Override
    public ApiResponse<CurrencyDto> getCurrency(@RequestBody CurrencyRequest request) {
        return ok(currencyService.getCurrency(request));
    }

    @GetMapping
    @Override
    public ApiResponse<List<CurrencyDto>> updateCurrencies() {
        return ok(currencyService.updateCurrencies());
    }
}
