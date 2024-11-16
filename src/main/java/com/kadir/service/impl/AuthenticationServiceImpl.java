package com.kadir.service.impl;

import com.kadir.dto.AuthRequest;
import com.kadir.dto.AuthResponse;
import com.kadir.dto.DtoUser;
import com.kadir.dto.RefreshTokenRequest;
import com.kadir.exception.BaseException;
import com.kadir.exception.ErrorMessage;
import com.kadir.exception.MessageType;
import com.kadir.jwt.JWTService;
import com.kadir.model.RefreshToken;
import com.kadir.model.User;
import com.kadir.repository.RefreshTokenRepository;
import com.kadir.repository.UserRepository;
import com.kadir.service.IAuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private User createUser(AuthRequest input) {
        User user = new User();
        user.setCreatedAt(new Date());
        user.setUsername(input.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));
        user.setRole(input.getRole());
        user.setEmail(input.getEmail());
        user.setPhoneNumber(input.getPhoneNumber());

        return user;
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreatedAt(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    public boolean isValidRefreshToken(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    @Override
    public DtoUser register(AuthRequest input) {
        DtoUser dtoUser = new DtoUser();
        User savedUser = userRepository.save(createUser(input));
        BeanUtils.copyProperties(savedUser, dtoUser);
        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<User> optUser = userRepository.findByUsername(input.getUsername());

            String accessToken = jwtService.generateToken(optUser.get());
            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));
            return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest input) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(input.getRefreshToken());
        if (optionalRefreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, input.getRefreshToken()));
        }
        if (!isValidRefreshToken(optionalRefreshToken.get().getExpiredDate())) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, input.getRefreshToken()));
        }
        User user = optionalRefreshToken.get().getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));
        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }

}
