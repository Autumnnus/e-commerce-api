package com.kadir.modules.paymentmethods.wallet.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.paymentmethods.wallet.dto.WalletDto;
import com.kadir.modules.paymentmethods.wallet.dto.WalletIUDto;

public interface IWalletController {

    ApiResponse<WalletDto> addFunds(WalletIUDto walletIUDto);

    ApiResponse<WalletDto> withdrawFunds(WalletIUDto walletIUDto);

    ApiResponse<WalletDto> getWallet();
}
