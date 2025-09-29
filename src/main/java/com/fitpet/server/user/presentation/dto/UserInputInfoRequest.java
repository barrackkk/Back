package com.fitpet.server.user.presentation.dto;

import com.fitpet.server.user.domain.entity.Gender;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserInputInfoRequest(
    @NotNull
    @Length(min = 5, max = 20, message = "닉네임은 5자 이상 20자 미만이어야 합니다.")
    String nickname,

    @NotNull
    int age,

    @NotNull
    Gender gender,

    @NotNull
    Double weightKg,
    @NotNull
    Double heightCm,
    Double targetWeightKg,
    Double pbf,
    Double targetPbf
) {
}
