package com.kadir.modules.authentication.controller.impl;

import com.kadir.common.controller.RootEntity;
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
    public RootEntity<CustomerDto> registerCustomer(@Valid @RequestBody AuthCustomerRegisterRequest input) {
        return RootEntity.success(authenticationService.registerCustomer(input));
    }

    @PostMapping("/registerSeller")
    @Override
    public RootEntity<SellerDto> registerSeller(@Valid @RequestBody AuthSellerRegisterRequest input) {
        return RootEntity.success(authenticationService.registerSeller(input));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input) {
        return RootEntity.success(authenticationService.authenticate(input));
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshToken) {
        return RootEntity.success(authenticationService.refreshToken(refreshToken));
    }

}
