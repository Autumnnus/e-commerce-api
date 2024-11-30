package com.kadir.modules.authentication.service;

import com.kadir.modules.authentication.dto.*;

public interface IAuthenticationService {

    DtoCustomer registerCustomer(AuthCustomerRegisterRequest input);

    DtoSeller registerSeller(AuthSellerRegisterRequest input);

    AuthResponse authenticate(AuthRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);
}
