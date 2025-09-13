package com.fitpet.server.auth.presentation.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
