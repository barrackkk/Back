package com.fitpet.server.domain.dailywalk.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fitpet.server.domain.dailywalk.mapper.DailyWalkMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DailyWalkServiceImpl implements DailyWalkService {

    private final DailyWalkRepository dailyWalkRepository;
    private final DailyWalkMapper dailyWalkMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<DailyWalk> getAllByUserId(@NotNull Long userId) {
        log.debug("[DailyWalkService] 전체 조회 요청: userId={}", userId);
        List<DailyWalk> result = dailyWalkRepository.findAllByUser_Id(userId);
        log.info("[DailyWalkService] 전체 조회 완료: userId={}, count={}", userId, result.size());
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public DailyWalk getDailyWalkByUserIdAndDate(@NotNull Long userId,
                                                 @NotNull @PastOrPresent LocalDate date) {
        log.debug("[DailyWalkService] 사용자의 해당 날짜 조회 요청 : userId={}, date={} ", userId, date);

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = start.plusDays(1);
        DailyWalk found = dailyWalkRepository.findFirstByUser_IdAndCreatedAtBetweenOrderByCreatedAtDesc(userId, start, end)
                .orElseThrow(() -> {
                    log.warn("[DailyWalkService] 사용자의 해당 날짜 조회 실패 : userId={}, date={} ", userId, date);
                    return new DailyWalkNotFoundException();
                });

        log.info("[DailyWalkService] 사용자의 해당 날짜 조회 성공 : id={}, userId={}, date={} ", found.getId(), userId, date);
        return found;
    }

    @Override
    public DailyWalk createDailyWalk(@Valid DailyWalkCreateRequest req) {
        log.debug("[DailyWalkService] 생성 요청: userId={}, step={}, distanceKm={}, burnCalories={}, createdAt={}",
                req.userId(), req.step(), req.distanceKm(), req.burnCalories(), req.date());

        if (entityManager.find(User.class, req.userId()) == null) {
            log.warn("[DailyWalkService] 생성 실패 - 사용자 없음: userId={}", req.userId());
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        User userRef = entityManager.getReference(User.class, req.userId());
        LocalDateTime created = (req.date() != null) ? req.date().atStartOfDay() : LocalDateTime.now();
        DailyWalk toSave = dailyWalkMapper.toEntity(req, userRef, created);

        toSave.validateInvariants();
        DailyWalk saved = dailyWalkRepository.save(toSave);

        log.info("[DailyWalkService] 생성 완료: dailyWalkId={}, userId={}, createdAt={}",
                saved.getId(), userRef.getId(), saved.getCreatedAt());
        return saved;
    }


    @Override
    public void updateDailyWalkStep(@NotNull Long userId,
                                    @NotNull @PastOrPresent LocalDate date,
                                    @NotNull @PositiveOrZero Integer newStep) {
        log.debug("[DailyWalkService] 걸음수 수정 요청: userId={}, date={}, newStep={}", userId, date, newStep);

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = start.plusDays(1);

        int updated = dailyWalkRepository.updateStepByUserIdAndDate(userId, start, end, newStep);
        if (updated == 0) {
            log.warn("[DailyWalkService] 걸음수 수정 실패(대상 없음): userId={}, date={}", userId, date);
            throw new DailyWalkNotFoundException();
        }

        log.info("[DailyWalkService] 걸음수 수정 완료: userId={}, date={}, newStep={}", userId, date, newStep);
    }


    @Override
    public void deleteDailyWalk(@NotNull Long dailyWalkId) {
        log.debug("[DailyWalkService] 삭제 요청: dailyWalkId={}", dailyWalkId);
        if (!dailyWalkRepository.existsById(dailyWalkId)) {
            log.warn("[DailyWalkService] 삭제 실패(대상 없음): dailyWalkId={}", dailyWalkId);
            throw new DailyWalkNotFoundException();
        }

        dailyWalkRepository.deleteById(dailyWalkId);
        log.info("[DailyWalkService] 삭제 완료: dailyWalkId={}", dailyWalkId);
    }
}