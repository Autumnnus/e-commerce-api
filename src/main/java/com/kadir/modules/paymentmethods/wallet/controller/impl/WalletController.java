package com.kadir.modules.paymentmethods.wallet.controller.impl;

import com.kadir.common.constants.Paths;
import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.paymentmethods.wallet.controller.IWalletController;
import com.kadir.modules.paymentmethods.wallet.dto.WalletDto;
import com.kadir.modules.paymentmethods.wallet.dto.WalletIUDto;
import com.kadir.modules.paymentmethods.wallet.service.IWalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Paths.BASE_PATH + "/wallet")
public class WalletController extends RestBaseController implements IWalletController {

    @Autowired
    private IWalletService walletService;

    @PatchMapping("/add-funds")
    @Override
    public ApiResponse<WalletDto> addFunds(@RequestBody @Valid WalletIUDto walletIUDto) {
        return ok(walletService.addFunds(walletIUDto));
    }

    @PatchMapping("/withdraw-funds")
    @Override
    public ApiResponse<WalletDto> withdrawFunds(@RequestBody @Valid WalletIUDto walletIUDto) {
        return ok(walletService.withdrawFunds(walletIUDto));
    }

    @GetMapping
    @Override
    public ApiResponse<WalletDto> getWallet() {
        return ok(walletService.getWallet());
    }
}
