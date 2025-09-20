package com.fitpet.server.pet.application.service;

import com.fitpet.server.pet.presentation.dto.PetCreateRequest;
import com.fitpet.server.pet.presentation.dto.PetDto;

public interface PetService {
    PetDto create(Long ownerId, PetCreateRequest request);
}
