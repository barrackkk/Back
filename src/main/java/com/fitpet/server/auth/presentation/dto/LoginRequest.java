package com.fitpet.server.auth.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8, max = 72, message = "8자 이상이어야 합니다.") String password
) {
}

