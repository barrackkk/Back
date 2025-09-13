package com.fitpet.server.auth.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email String email,
        @NotBlank String password
) {
}

