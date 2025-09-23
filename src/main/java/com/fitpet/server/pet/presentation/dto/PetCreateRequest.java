package com.fitpet.server.pet.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetCreateRequest(
    @NotBlank String name,
    @NotNull String petType // enum 문자열로 받음: "DOG", "CAT"
) {
}