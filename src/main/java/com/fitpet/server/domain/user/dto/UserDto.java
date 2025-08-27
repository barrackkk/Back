package com.fitpet.server.domain.user.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserDto(
    UUID userId,
    String nickname,
    String email
) {
}
