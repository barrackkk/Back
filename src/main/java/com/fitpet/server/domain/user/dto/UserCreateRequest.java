package com.fitpet.server.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserCreateRequest(
    @NotBlank
    @Email
    String email,

    @NotBlank
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,100}$",
        message = "비밀번호는 영어, 숫자, 특수문자를 포함해야 합니다."
    )
    String password,

    @NotBlank
    @Size(min = 5, max = 20)
    String nickname
) {
}
