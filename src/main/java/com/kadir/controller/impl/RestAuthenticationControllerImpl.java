package com.kadir.controller.impl;

import com.kadir.controller.IRestAuthenticationController;
import com.kadir.controller.RootEntity;
import com.kadir.dto.*;
import com.kadir.service.IAuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;


    @PostMapping("/registerCustomer")
    @Override
    public RootEntity<DtoCustomer> registerCustomer(@Valid @RequestBody AuthCustomerRegisterRequest input) {
        return RootEntity.success(authenticationService.registerCustomer(input));
    }

    @PostMapping("/registerSeller")
    @Override
    public RootEntity<DtoSeller> registerSeller(@Valid @RequestBody AuthSellerRegisterRequest input) {
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
