package com.kadir.modules.authentication.controller;

import com.kadir.common.controller.ApiResponse;
import com.kadir.modules.authentication.dto.*;

public interface IAuthenticationController {

    ApiResponse<CustomerDto> registerCustomer(AuthCustomerRegisterRequest input);

    ApiResponse<SellerDto> registerSeller(AuthSellerRegisterRequest input);

    ApiResponse<AuthResponse> authenticate(AuthRequest input);

    ApiResponse<AuthResponse> refreshToken(RefreshTokenRequest refreshToken);
}
