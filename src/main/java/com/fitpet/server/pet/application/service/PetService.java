package com.fitpet.server.pet.application.service;

import com.fitpet.server.pet.presentation.dto.PetCreateRequest;
import com.fitpet.server.pet.presentation.dto.PetDto;
import com.fitpet.server.pet.presentation.dto.PetUpdateRequest;

public interface PetService {
    PetDto createPet(Long ownerId, PetCreateRequest request);

    void deletePet(Long ownerId, Long petId);

    PetDto findPet(Long ownerId, Long petId);

    PetDto updatePet(Long owerId, Long petId, PetUpdateRequest request);
}
