package com.kadir.modules.paymentmethods.creditcard.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardCreateDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardUpdateDto;

import java.util.List;

public interface ICreditCardController {

    ApiResponse<CreditCardDto> addCreditCard(CreditCardCreateDto creditCardCreateDto);

    ApiResponse<CreditCardDto> updateCreditCard(Long creditCardId, CreditCardUpdateDto creditCardUpdateDto);

    ApiResponse<CreditCardDto> deleteCreditCard(Long creditCardId);

    ApiResponse<CreditCardDto> getCreditCard(Long creditCardId);

    ApiResponse<List<CreditCardDto>> getCreditCards();
}
