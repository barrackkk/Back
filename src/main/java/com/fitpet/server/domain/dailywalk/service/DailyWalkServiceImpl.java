package com.fitpet.server.domain.dailywalk.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<DailyWalk> getAllByUserId(Long userId) {
        return dailyWalkRepository.findAllByUser_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public DailyWalk getDailyWalkByUserIdAndDate(Long userId, java.time.LocalDate date) {
        LocalDateTime key = date.atStartOfDay(); // yyyy-MM-ddT00:00:00
        return dailyWalkRepository.findByUser_IdAndCreatedAt(userId, key)
                .orElseThrow(DailyWalkNotFoundException::new);
    }

    @Override
    public DailyWalk createDailyWalk(DailyWalkCreateRequest req) {
        // 사용자 존재 확인
        if (em.find(User.class, req.userId()) == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        User userRef = em.getReference(User.class, req.userId());

        // createdAt 정규화: (요청값 있으면 그 '날짜'의 자정, 없으면 '오늘' 자정)
        LocalDateTime createdKey = (req.createdAt() == null)
                ? LocalDate.now().atStartOfDay()
                : req.createdAt().toLocalDate().atStartOfDay();

        // 하루 1행 보장: 중복 검사
        if (dailyWalkRepository.existsByUser_IdAndCreatedAt(userRef.getId(), createdKey)) {
            throw new BusinessException(ErrorCode.DAILY_WALK_ALREADY_EXISTS);
        }

        DailyWalk toSave = DailyWalk.builder()
                .user(userRef)
                .step(req.step())
                .distanceKm(req.distanceKm())
                .burnCalories(req.burnCalories())
                .createdAt(createdKey)
                .build();

        toSave.validateInvariants();
        return dailyWalkRepository.save(toSave);
    }

    @Override
    public void updateDailyWalkStep(Long userId, java.time.LocalDate date, Integer newStep) {
        LocalDateTime key = date.atStartOfDay();
        int updated = dailyWalkRepository.updateStepByUserIdAndCreatedAt(userId, key, newStep);
        if (updated == 0) throw new DailyWalkNotFoundException();
    }

    @Override
    public void deleteDailyWalk(Long dailyWalkId) {
        if (!dailyWalkRepository.existsById(dailyWalkId)) {
            throw new DailyWalkNotFoundException();
        }
        dailyWalkRepository.deleteById(dailyWalkId);
    }
}
