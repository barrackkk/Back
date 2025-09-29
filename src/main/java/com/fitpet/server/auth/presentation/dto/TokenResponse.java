package com.fitpet.server.auth.presentation.dto;

import com.fitpet.server.user.domain.entity.RegistrationStatus;

public record TokenResponse(
        boolean success,
        RegistrationStatus registrationStatus,
        String serverAccessToken,
        String serverRefreshToken
) {

    public static TokenResponse success(RegistrationStatus registrationStatus,
                                        String accessToken,
                                        String refreshToken) {
        return new TokenResponse(true, registrationStatus, accessToken, refreshToken);
    }

    public TokenResponse withoutRefreshToken() {
        return new TokenResponse(success, registrationStatus, serverAccessToken, null);
    }
}
