package com.kadir.modules.currency.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kadir.common.enums.CurrencyCode;
import com.kadir.common.exception.BaseException;
import com.kadir.common.exception.ErrorMessage;
import com.kadir.common.exception.MessageType;
import com.kadir.common.utils.currency.CurrencyUtils;
import com.kadir.modules.currency.dto.CurrencyDto;
import com.kadir.modules.currency.dto.CurrencyRequest;
import com.kadir.modules.currency.model.Currency;
import com.kadir.modules.currency.repository.CurrencyRepository;
import com.kadir.modules.currency.service.ICurrencyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {

    private final ModelMapper modelMapper;
    private final CurrencyRepository currencyRepository;

    @Value("${currency.api.url}")
    private String apiUrl;

    @Value("${currency.api.key}")
    private String apiKey;

    @Override
    public List<CurrencyDto> getAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        return modelMapper.map(currencies, List.class);
    }

    @Override
    public CurrencyDto getCurrency(CurrencyRequest request) {
        Currency currency = currencyRepository.findByCurrencyCode(request.getCurrencyCode())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CurrencyCode not found")));
        Currency sourceCurrency = currencyRepository.findByCurrencyCode(request.getTargetCurrencyCode())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.GENERAL_EXCEPTION, "CurrencyCode not found")));
        currency.setExchangeRate(currency.getExchangeRate() / sourceCurrency.getExchangeRate());

        return modelMapper.map(currency, CurrencyDto.class);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public List<CurrencyDto> updateCurrencies() {
        List<Currency> currencyList = new ArrayList<>();

        try {
            URL url = new URL(apiUrl + apiKey + "/latest/USD");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String response = in.lines().collect(Collectors.joining());

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response);

                    JsonNode conversionRatesNode = rootNode.get("conversion_rates");
                    if (conversionRatesNode != null) {
                        conversionRatesNode.fields().forEachRemaining(entry -> {
                            String currencyCode = entry.getKey();
                            Currency currencyUtil = CurrencyUtils.getCurrency(CurrencyCode.valueOf(currencyCode));
                            double exchangeRate = entry.getValue().asDouble();

                            Currency existingCurrency = currencyRepository.findByCurrencyCode(CurrencyCode.valueOf(currencyCode))
                                    .orElse(new Currency());

                            existingCurrency.setCurrencyCode(CurrencyCode.valueOf(currencyCode));
                            existingCurrency.setExchangeRate(exchangeRate);
                            existingCurrency.setSymbol(currencyUtil.getSymbol());
                            existingCurrency.setCurrencyName(currencyUtil.getCurrencyName());
                            existingCurrency.setIsDefault(currencyCode.equals(CurrencyCode.USD.name()));

                            currencyList.add(existingCurrency);
                        });
                    }
                }
            } else {
                System.err.println("HTTP Error Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Currency> savedCurrencies = currencyRepository.saveAll(currencyList);
        return modelMapper.map(savedCurrencies, List.class);
    }

}
