package com.kadir.modules.paymentmethods.wallet.service;

import com.kadir.modules.paymentmethods.wallet.dto.WalletDto;
import com.kadir.modules.paymentmethods.wallet.dto.WalletIUDto;

public interface IWalletService {

    WalletDto addFunds(WalletIUDto walletIUDto);

    WalletDto withdrawFunds(WalletIUDto walletIUDto);

    WalletDto getWallet();
}
