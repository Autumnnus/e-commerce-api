package com.kadir.modules.paymentmethods.wallet.dto;

import com.kadir.common.dto.BaseDto;
import com.kadir.modules.authentication.dto.UserDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto extends BaseDto {

    private UserDto user;

    private BigDecimal balance;
}
