package com.fitpet.server.domain.dailywalk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fitpet.server.domain.dailywalk.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.domain.dailywalk.entity.DailyWalk;
import com.fitpet.server.domain.dailywalk.exception.DailyWalkNotFoundException;
import com.fitpet.server.domain.dailywalk.repository.DailyWalkRepository;
import com.fitpet.server.domain.user.entity.User;
import com.fitpet.server.global.exception.BusinessException;
import com.fitpet.server.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // 기본 쓰기 트랜잭션
public class DailyWalkServiceImpl implements DailyWalkService {

    private final DailyWalkRepository dailyWalkRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<DailyWalk> getDailyWalksByUser(@NotNull Long userId) {
        return dailyWalkRepository.findAllByUser_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyWalk getDailyWalkByUserAndCreatedAt(@NotNull Long userId,
                                                    @NotNull @PastOrPresent LocalDateTime createdAt) {
        return dailyWalkRepository.findByUser_IdAndCreatedAt(userId, createdAt)
                .orElseThrow(DailyWalkNotFoundException::new);
    }

    @Override
    public DailyWalk createDailyWalk(@Valid DailyWalkCreateRequest req) {
        if (em.find(User.class, req.userId()) == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        User userRef = em.getReference(User.class, req.userId());

        DailyWalk toSave = DailyWalk.builder()
                .user(userRef)
                .step(req.step())
                .distanceKm(req.distanceKm())
                .burnCalories(req.burnCalories())
                .createdAt(Objects.requireNonNullElseGet(req.createdAt(), LocalDateTime::now))
                .build();

        toSave.validateInvariants();
        return dailyWalkRepository.save(toSave);
    }

    @Override
    public void updateDailyWalkStep(@NotNull Long userId,
                                    @NotNull @PastOrPresent LocalDateTime createdAt,
                                    @NotNull @PositiveOrZero Integer newStep) {
        int updated = dailyWalkRepository.updateStepByUserIdAndCreatedAt(userId, createdAt, newStep);
        if (updated == 0) throw new DailyWalkNotFoundException();
    }

    @Override
    public void deleteDailyWalk(@NotNull Long dailyWalkId) {
        if (!dailyWalkRepository.existsById(dailyWalkId)) {
            throw new DailyWalkNotFoundException();
        }
        dailyWalkRepository.deleteById(dailyWalkId);
    }
}
