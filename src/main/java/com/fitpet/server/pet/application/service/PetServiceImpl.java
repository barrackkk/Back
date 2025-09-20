package com.fitpet.server.pet.application.service;

import com.fitpet.server.pet.application.mapper.PetMapper;
import com.fitpet.server.pet.domain.entity.Pet;
import com.fitpet.server.pet.domain.exception.PetAlreadyExistsException;
import com.fitpet.server.pet.domain.repository.PetRepository;
import com.fitpet.server.pet.presentation.dto.PetCreateRequest;
import com.fitpet.server.pet.presentation.dto.PetDto;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.exception.UserNotFoundException;
import com.fitpet.server.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PetDto create(Long ownerId, PetCreateRequest request) {
        log.info("[PetService] Pet 생성 시작: ownerId ={}", ownerId);
        User owner = userRepository.findById(ownerId).orElseThrow(UserNotFoundException::new);

        // 사용자가 펫을 이미 소유하고 있는 경우 예외처리
        if (petRepository.existsByOwnerId(ownerId)) {
            throw new PetAlreadyExistsException();
        }

        try {
            Pet pet = petMapper.toEntity(request, owner);
            Pet saved = petRepository.save(pet);
            log.info("[PetService] Pet 생성 완료: ownerId={}, petId={}", ownerId, saved.getId());
            return petMapper.toDto(saved);

        } catch (DataIntegrityViolationException e) {

            log.warn("[PetService] 중복 생성 감지: ownerId={}", ownerId, e);
            throw new PetAlreadyExistsException();
        }
    }
}
