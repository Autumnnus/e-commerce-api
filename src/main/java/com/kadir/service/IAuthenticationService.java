package com.kadir.service;

import com.kadir.dto.AuthRequest;
import com.kadir.dto.AuthResponse;
import com.kadir.dto.DtoUser;
import com.kadir.dto.RefreshTokenRequest;

public interface IAuthenticationService {

    DtoUser register(AuthRequest input);

    AuthResponse authenticate(AuthRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);
}
