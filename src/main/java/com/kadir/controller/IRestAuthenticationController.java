package com.kadir.controller;

import com.kadir.dto.*;

public interface IRestAuthenticationController {

    RootEntity<DtoCustomer> registerCustomer(AuthCustomerRegisterRequest input);

    RootEntity<DtoSeller> registerSeller(AuthSellerRegisterRequest input);

    RootEntity<AuthResponse> authenticate(AuthRequest input);

    RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshToken);
}
