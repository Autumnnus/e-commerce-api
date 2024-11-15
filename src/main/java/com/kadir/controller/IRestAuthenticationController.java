package com.kadir.controller;

import com.kadir.dto.AuthRequest;
import com.kadir.dto.AuthResponse;
import com.kadir.dto.DtoUser;
import com.kadir.dto.RefreshTokenRequest;

public interface IRestAuthenticationController {

    RootEntity<DtoUser> register(AuthRequest input);

    RootEntity<AuthResponse> authenticate(AuthRequest input);

    RootEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshToken);
}
