package com.fitpet.server.alram.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlramResponseDto {
    private Long alramId;
    private String fcmMessageId; // FCM이 반환한 메시지 ID
    private String message;
}