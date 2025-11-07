package com.fitpet.server.alram.presentation.dto.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlramResponseDto {
    private Long alramId;
    private String fcmMessageId; // FCM이 반환한 메시지 ID
    private String message;
    private Map<String, String> data;
}