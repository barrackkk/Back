package com.fitpet.server.dailyworkout.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionEndResponse {
    private Long sessionId;
    private String message;
}