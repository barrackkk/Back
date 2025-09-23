package com.fitpet.server.auth.presentation.dto;

public record GoogleProfile(String sub, String email, boolean emailVerified) {
}
