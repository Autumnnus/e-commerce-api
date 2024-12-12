package com.kadir.modules.paymentmethods.creditcard.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.modules.address.dto.AddressDto;
import lombok.Data;

@Data
public class CreditCardDto extends BaseDto {

    private String cardHolderName;

    private String cardNumber;

    private String expirationDate;

    private AddressDto address;

    private Long userId;
}
