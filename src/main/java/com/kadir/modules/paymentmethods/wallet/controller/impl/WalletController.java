package com.kadir.modules.paymentmethods.wallet.controller.impl;

import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.paymentmethods.wallet.controller.IWalletController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/wallet")
public class WalletController extends RestBaseController implements IWalletController {
}
