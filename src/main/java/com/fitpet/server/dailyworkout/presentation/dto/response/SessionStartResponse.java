package com.fitpet.server.dailyworkout.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionStartResponse {
    private Long sessionId;
    private String message;
}