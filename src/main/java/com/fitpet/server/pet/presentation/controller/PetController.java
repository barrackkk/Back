package com.fitpet.server.pet.presentation.controller;

import com.fitpet.server.pet.application.service.PetService;
import com.fitpet.server.pet.presentation.dto.PetCreateRequest;
import com.fitpet.server.pet.presentation.dto.PetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pets")
@Slf4j
public class PetController {
    private final PetService petService;

    @PostMapping("/{userId}")
    public ResponseEntity<PetDto> create(
        @PathVariable Long userId,
        @Valid @RequestBody PetCreateRequest request
    ) {

        log.info("[PetController] 펫 생성 요청 : petName = {} , petType = {},  ownerId = {}",
            request.name(), request.petType(), userId);

        PetDto createdPet = petService.create(userId, request);

        log.info("[PetController] 펫 생성 완료 : petName = {} , petType = {},  ownerId = {}",
            request.name(), request.petType(), userId);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdPet);
    }

}
