package com.kadir.modules.authentication.controller;

import com.kadir.common.controller.RootEntity;
import com.kadir.modules.authentication.dto.*;

public interface IAuthenticationController {

    RootEntity<CustomerDto> registerCustomer(AuthCustomerRegisterRequest input);

    RootEntity<SellerDto> registerSeller(AuthSellerRegisterRequest input);

    RootEntity<AuthResponse> authenticate(AuthRequest input);

    RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshToken);
}
