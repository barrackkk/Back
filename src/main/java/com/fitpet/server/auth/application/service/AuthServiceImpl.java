package com.fitpet.server.auth.application.service;

import com.fitpet.server.auth.domain.exception.InvalidAccessTokenException;
import com.fitpet.server.auth.domain.exception.InvalidLogin;
import com.fitpet.server.auth.domain.exception.InvalidRefreshToken;
import com.fitpet.server.auth.infra.GoogleTokenVerifier;
import com.fitpet.server.auth.infra.GoogleTokenVerifier.GoogleProfile;
import com.fitpet.server.auth.infra.KakaoClient;
import com.fitpet.server.auth.infra.KakaoClient.KakaoProfile;
import com.fitpet.server.auth.infra.RedisTokenRepository;
import com.fitpet.server.auth.presentation.dto.LoginRequest;
import com.fitpet.server.auth.presentation.dto.TokenResponse;
import com.fitpet.server.security.jwt.JwtTokenProvider;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.exception.UserNotFoundException;
import com.fitpet.server.user.domain.repository.UserRepository;
import java.util.List;
import java.util.UUID;
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
    private final KakaoClient kakaoClient;
    private final GoogleTokenVerifier googleTokenVerifier;

    @Override
    @Transactional
    public TokenResponse login(LoginRequest request) {
        log.info("[AuthService] 로그인 시도");
        User user = userRepository.findByEmail(request.email())
            .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidLogin();
        }

        // 임시로 ROLE_USER
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

    @Override
    @Transactional
    public TokenResponse loginWithGoogle(String idToken) {
        GoogleProfile profile = googleTokenVerifier.verifyIdToken(idToken);
        String email = profile.email();
        String providerUid = profile.sub();

        User user = upsertOAuthUser(email, "GOOGLE", providerUid);
        return issueTokens(user);
    }

    @Override
    @Transactional
    public TokenResponse loginWithKakao(String kakaoAccessToken) {
        KakaoProfile profile = kakaoClient.getProfile(kakaoAccessToken);
        String email = (profile.email() == null || profile.email().isBlank())
            ? ("kakao_" + profile.id() + "@anon.kakao.local")
            : profile.email();

        User user = upsertOAuthUser(email, "KAKAO", profile.id());
        return issueTokens(user);
    }

    // 구글, 카카오 로그인으로 한 사용자가 없으면 새로 만들어 저장 , 있으면 그대로 사용(업데이트x)
    private User upsertOAuthUser(String email, String provider, String providerUid) {
        return userRepository.findByEmail(email)
            .orElseGet(() -> {
                String dummyPw = passwordEncoder.encode(UUID.randomUUID().toString());
                User u = User.builder()
                    .email(email)
                    .password(dummyPw)      // password not null인 스키마 대응
                    .nickname(email.split("@")[0])
                    .provider(provider)     // 엔티티에 provider 필드 있음
                    .build();
                return userRepository.save(u);
            });
    }

    private TokenResponse issueTokens(User user) {
        String access = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), List.of("ROLE_USER"));
        String refresh = jwtTokenProvider.generateRefreshToken(user.getId(), user.getEmail());
        redisTokenRepository.save(user.getId(), refresh, jwtTokenProvider.getRefreshExpirationMs());
        return new TokenResponse(access, refresh);
    }
}