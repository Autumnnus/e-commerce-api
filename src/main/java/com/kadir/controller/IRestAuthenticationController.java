package com.kadir.controller;

import com.kadir.dto.*;

public interface IRestAuthenticationController {

    RootEntity<DtoUser> register(AuthRegisterRequest input);

    RootEntity<AuthResponse> authenticate(AuthRequest input);

    RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshToken);
}
