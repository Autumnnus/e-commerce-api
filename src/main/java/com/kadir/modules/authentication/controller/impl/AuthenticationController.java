package com.kadir.modules.authentication.controller.impl;

import com.kadir.common.controller.ApiResponse;
import com.kadir.common.controller.impl.RestBaseController;
import com.kadir.modules.authentication.controller.IAuthenticationController;
import com.kadir.modules.authentication.dto.*;
import com.kadir.modules.authentication.service.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController extends RestBaseController implements IAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;


    @PostMapping("/registerCustomer")
    @Override
    public ApiResponse<CustomerDto> registerCustomer(@Valid @RequestBody AuthCustomerRegisterRequest input) {
        return ApiResponse.success(authenticationService.registerCustomer(input));
    }

    @PostMapping("/registerSeller")
    @Override
    public ApiResponse<SellerDto> registerSeller(@Valid @RequestBody AuthSellerRegisterRequest input) {
        return ApiResponse.success(authenticationService.registerSeller(input));
    }

    @PostMapping("/authenticate")
    @Override
    public ApiResponse<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input) {
        return ApiResponse.success(authenticationService.authenticate(input));
    }

    @PostMapping("/refreshToken")
    @Override
    public ApiResponse<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshToken) {
        return ApiResponse.success(authenticationService.refreshToken(refreshToken));
    }

}
