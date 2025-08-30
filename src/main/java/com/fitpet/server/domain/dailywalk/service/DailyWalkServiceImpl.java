package com.fitpet.server.domain.dailywalk.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class DailyWalkServiceImpl implements DailyWalkService {

    private final DailyWalkRepository dailyWalkRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<DailyWalk> getAllByUserId(@NotNull Long userId) {
        return dailyWalkRepository.findAllByUser_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyWalk getDailyWalkByUserIdAndDate(@NotNull Long userId,
                                                 @NotNull @PastOrPresent LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = start.plusDays(1);
        return dailyWalkRepository.findByUserIdAndDate(userId, start, end)
                .orElseThrow(DailyWalkNotFoundException::new);
    }

    @Override
    public DailyWalk createDailyWalk(@Valid DailyWalkCreateRequest req) {
        if (entityManager.find(User.class, req.userId()) == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        User userRef = entityManager.getReference(User.class, req.userId());

        LocalDateTime created = (req.createdAt() != null) ? req.createdAt() : LocalDateTime.now();

        DailyWalk toSave = DailyWalk.builder()
                .user(userRef)
                .step(req.step())
                .distanceKm(req.distanceKm())
                .burnCalories(req.burnCalories())
                .createdAt(created)
                .build();

        toSave.validateInvariants();
        return dailyWalkRepository.save(toSave);
    }

    @Override
    public void updateDailyWalkStep(@NotNull Long userId,
                                    @NotNull @PastOrPresent LocalDate date,
                                    @NotNull @PositiveOrZero Integer newStep) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = start.plusDays(1);
        int updated = dailyWalkRepository.updateStepByUserIdAndDate(userId, start, end, newStep);
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