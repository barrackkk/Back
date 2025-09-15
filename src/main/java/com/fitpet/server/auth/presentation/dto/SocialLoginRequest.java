package com.fitpet.server.auth.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // 혹시 모르는 여분 필드 무시
public record SocialLoginRequest(
    String idToken,      // 구글에서 오는 ID 토큰
    String accessToken   // 카카오에서 오는 액세스 토큰
) {
}