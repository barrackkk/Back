package com.fitpet.server.auth.application.service;

import com.fitpet.server.auth.domain.exception.InvalidAccessTokenException;
import com.fitpet.server.auth.domain.exception.InvalidLogin;
import com.fitpet.server.auth.domain.exception.InvalidRefreshToken;
import com.fitpet.server.auth.infra.RedisTokenRepository;
import com.fitpet.server.auth.presentation.dto.LoginRequest;
import com.fitpet.server.auth.presentation.dto.TokenResponse;
import com.fitpet.server.security.jwt.JwtTokenProvider;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.exception.UserNotFoundException;
import com.fitpet.server.user.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenRepository redisTokenRepository;

    @Override
    @Transactional
    public TokenResponse login(LoginRequest request) {
        log.info("[AuthService] 로그인 시도");
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidLogin();
        }

        String access = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), List.of("ROLE_USER"));
        String refresh = jwtTokenProvider.generateRefreshToken(user.getId(), user.getEmail());

        redisTokenRepository.save(user.getId(), refresh, jwtTokenProvider.getRefreshExpirationMs());

        return new TokenResponse(access, refresh);
    }

    @Override
    public TokenResponse refresh(String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new InvalidRefreshToken();
        }

        Long userId = jwtTokenProvider.getUserId(refreshToken, true);

        String saved = redisTokenRepository.find(userId);
        if (saved == null || !saved.equals(refreshToken)) {
            throw new InvalidRefreshToken();
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String newAccess = jwtTokenProvider.generateAccessToken(
            user.getId(),
            user.getEmail(),
            List.of("ROLE_USER")
        );

        return new TokenResponse(newAccess, refreshToken); // refresh 그대로 재사용
    }

    @Override
    public void logout(String accessToken) {
        log.info("[AuthService] 로그아웃 시도");

        if (!jwtTokenProvider.validateAccessToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        Long userId = jwtTokenProvider.getUserId(accessToken, false);

        redisTokenRepository.delete(userId);
    }
}