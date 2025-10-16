package com.fitpet.server.pet.application.service;

import com.fitpet.server.pet.application.mapper.PetMapper;
import com.fitpet.server.pet.domain.entity.Pet;
import com.fitpet.server.pet.domain.exception.PetAccessDeniedException;
import com.fitpet.server.pet.domain.exception.PetAlreadyExistsException;
import com.fitpet.server.pet.domain.exception.PetNotFoundException;
import com.fitpet.server.pet.domain.repository.PetRepository;
import com.fitpet.server.pet.presentation.dto.PetCreateRequest;
import com.fitpet.server.pet.presentation.dto.PetDto;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.exception.UserNotFoundException;
import com.fitpet.server.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
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
    public PetDto createPet(Long ownerId, PetCreateRequest request) {
        log.info("[PetService] Pet 생성 시작: ownerId ={}", ownerId);
        User owner = userRepository.findById(ownerId).orElseThrow(UserNotFoundException::new);

        try {
            Pet pet = petMapper.toEntity(request, owner);
            Pet saved = petRepository.save(pet);
            log.info("[PetService] Pet 생성 완료: ownerId={}, petId={}", ownerId, saved.getId());
            return petMapper.toDto(saved);

        } catch (DataIntegrityViolationException e) {
            if (isUniqueConstraintViolation(e)) {
                log.warn("[PetService] 중복 생성 감지: ownerId={}", ownerId, e);
                throw new PetAlreadyExistsException();
            }
            throw e;
        }
    }

    @Override
    @Transactional
    public void deletePet(Long ownerId, Long petId) {
        log.info("[PetService] Pet 삭제 시작: ownerId={}, petId={}", ownerId, petId);

        Pet pet = petRepository.findById(petId)
                .orElseThrow(PetNotFoundException::new);

        Long petOwnerId = pet.getOwner().getId();
        if (!petOwnerId.equals(ownerId)) {
            log.warn("[PetService] 삭제 권한 없음: 요청 ownerId={}, 실제 ownerId={}, petId={}",
                    ownerId, petOwnerId, petId);
            throw new PetAccessDeniedException();
        }

        petRepository.delete(pet);

        log.info("[PetService] Pet 삭제 완료: ownerId={}, petId={}", ownerId, petId);
    }

    @Override
    @Transactional(readOnly = true)
    public PetDto findPet(Long ownerId, Long petId) {
        log.info("[PetService] Pet 조회 시작: petId={}", petId);

        Pet pet = petRepository.findById(petId)
                .orElseThrow(PetNotFoundException::new);

        Long petOwnerId = pet.getOwner().getId();
        if (!petOwnerId.equals(ownerId)) {
            log.warn("[PetService] 조회 권한 없음: 요청 ownerId={}, 실제 ownerId={}, petId={}",
                    ownerId, petOwnerId, petId);
            throw new PetAccessDeniedException();
        }

        log.info("[PetService] Pet 조회 완료: petId={}", petId);

        return petMapper.toDto(pet);
    }


    // 전달 받은 예외가 유니크 제약 조건 위반인지 확인하는 메서드
    private boolean isUniqueConstraintViolation(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            // current가 cve 타입인지 확인
            if (current instanceof ConstraintViolationException cve) {
                String constraintName = cve.getConstraintName();
                if (constraintName != null) {
                    String lowered = constraintName.toLowerCase();
                    if (lowered.contains("unique") || lowered.contains("uk_")) {
                        return true;
                    }
                }
            }
            if (current instanceof java.sql.SQLIntegrityConstraintViolationException sqlEx) {
                if (isMysqlDuplicateKey(sqlEx)) {
                    return true;
                }
            }
            current = current.getCause();
        }
        return false;
    }

    private boolean isMysqlDuplicateKey(java.sql.SQLIntegrityConstraintViolationException sqlEx) {
        // MySQL 중복키 에러코드: 1062
        if (sqlEx.getErrorCode() == 1062) {
            return true;
        }
        String message = sqlEx.getMessage();
        if (message == null) {
            return false;
        }
        String lowered = message.toLowerCase();
        return lowered.contains("duplicate entry") || lowered.contains("duplicate key");
    }

}
