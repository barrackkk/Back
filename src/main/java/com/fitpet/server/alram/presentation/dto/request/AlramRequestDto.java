package com.fitpet.server.alram.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlramRequestDto {

    @NotNull(message = "알림을 받을 사용자 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "디바이스 토큰은 필수입니다.")
    private String targetDeviceToken;

    @NotBlank(message = "알림 제목은 필수입니다.")
    private String title;

    @NotBlank(message = "알림 내용은 필수입니다.")
    private String body;
}