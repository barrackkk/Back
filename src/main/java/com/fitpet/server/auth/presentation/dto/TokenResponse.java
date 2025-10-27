package com.fitpet.server.auth.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fitpet.server.user.domain.entity.RegistrationStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenResponse(
    boolean success,
    RegistrationStatus registrationStatus,
    String serverAccessToken,
    @JsonIgnore
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
