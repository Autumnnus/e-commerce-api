package com.kadir.modules.authentication.service;

import com.kadir.modules.authentication.dto.*;

public interface IAuthenticationService {

    CustomerDto registerCustomer(AuthCustomerRegisterRequest input);

    SellerDto registerSeller(AuthSellerRegisterRequest input);

    AuthResponse authenticate(AuthRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);
}
