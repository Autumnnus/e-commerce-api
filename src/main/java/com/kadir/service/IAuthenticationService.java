package com.kadir.service;

import com.kadir.dto.*;

public interface IAuthenticationService {

    DtoUser register(AuthRegisterRequest input);

    AuthResponse authenticate(AuthRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);
}
