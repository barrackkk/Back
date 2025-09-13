package com.fitpet.server.auth.presentation.controller;

import com.fitpet.server.auth.application.service.AuthService;
import com.fitpet.server.auth.presentation.dto.LoginRequest;
import com.fitpet.server.auth.presentation.dto.TokenResponse;
import com.fitpet.server.security.jwt.JwtTokenProvider;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // Login 시: Refresh를 쿠키로 내려주고, 응답 바디에는 access만 보냄
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        TokenResponse tokens = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("REFRESH_TOKEN", tokens.refreshToken())
            .httpOnly(true).secure(true)   // 운영할 때 반드시 true (HTTPS)
            .sameSite("None")              // 크로스 도메인일 때
            .path("/")
            .maxAge(Duration.ofMillis(jwtTokenProvider.getRefreshExpirationMs()))
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(new TokenResponse(tokens.accessToken(), null)); // refresh는 바디에 안 줌
    }

    // Refresh 시 쿠키에서 읽음
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@CookieValue("REFRESH_TOKEN") String refreshToken) {
        return ResponseEntity.ok(authService.refresh(refreshToken));
    }

    // Logout 시 서버에서 Redis 삭제 + 쿠키 만료
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String accessToken = authHeader.replace("Bearer ", "");
        authService.logout(accessToken);

        ResponseCookie expire = ResponseCookie.from("REFRESH_TOKEN", "")
            .httpOnly(true).secure(true)
            .sameSite("None").path("/")
            .maxAge(0) // 즉시 만료
            .build();

        return ResponseEntity.noContent()
            .header(HttpHeaders.SET_COOKIE, expire.toString())
            .build();
    }
}