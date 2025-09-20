package com.fitpet.server.pet.domain.repository;

import com.fitpet.server.pet.domain.entity.Pet;

public interface PetRepository {
    Pet save(Pet pet);

    boolean existsByOwnerId(Long ownerId);
}
