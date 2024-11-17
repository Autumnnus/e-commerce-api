package com.kadir.service;

import com.kadir.dto.*;

public interface IAuthenticationService {

    DtoCustomer registerCustomer(AuthCustomerRegisterRequest input);

    DtoSeller registerSeller(AuthSellerRegisterRequest input);

    AuthResponse authenticate(AuthRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);
}
