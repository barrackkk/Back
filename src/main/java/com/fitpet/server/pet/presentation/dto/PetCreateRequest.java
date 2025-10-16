package com.fitpet.server.pet.presentation.dto;

import com.fitpet.server.pet.domain.entity.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetCreateRequest(
        @NotBlank String name,
        @NotNull PetType petType,
        @NotNull String color
) {
}