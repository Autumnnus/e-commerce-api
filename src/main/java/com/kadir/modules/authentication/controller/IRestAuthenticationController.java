package com.kadir.modules.authentication.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.authentication.dto.*;

public interface IRestAuthenticationController {

    RootEntity<DtoCustomer> registerCustomer(AuthCustomerRegisterRequest input);

    RootEntity<DtoSeller> registerSeller(AuthSellerRegisterRequest input);

    RootEntity<AuthResponse> authenticate(AuthRequest input);

    RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshToken);
}
