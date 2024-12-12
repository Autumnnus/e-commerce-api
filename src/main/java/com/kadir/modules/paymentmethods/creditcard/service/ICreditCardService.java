package com.kadir.modules.paymentmethods.creditcard.service;

import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardCreateDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardDto;
import com.kadir.modules.paymentmethods.creditcard.dto.CreditCardUpdateDto;

import java.util.List;

public interface ICreditCardService {

    CreditCardDto addCreditCard(CreditCardCreateDto creditCardCreateDto);

    CreditCardDto updateCreditCard(Long creditCardId, CreditCardUpdateDto creditCardUpdateDto);

    CreditCardDto deleteCreditCard(Long creditCardId);

    CreditCardDto getCreditCard(Long creditCardId);

    List<CreditCardDto> getCreditCards();

}
