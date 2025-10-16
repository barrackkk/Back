package com.fitpet.server.pet.presentation.dto;

import com.fitpet.server.pet.domain.entity.PetType;

public record PetUpdateRequest(
        String name,
        PetType petType,
        String color
) {
}
