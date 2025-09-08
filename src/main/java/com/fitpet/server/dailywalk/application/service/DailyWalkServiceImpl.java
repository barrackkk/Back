package com.fitpet.server.dailywalk.application.service;

import com.fitpet.server.dailywalk.application.mapper.DailyWalkMapper;
import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import com.fitpet.server.dailywalk.domain.exception.DailyWalkNotFoundException;
import com.fitpet.server.dailywalk.domain.repository.DailyWalkRepository;
import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkStepUpdateRequest;
import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;
import com.fitpet.server.user.domain.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DailyWalkServiceImpl implements DailyWalkService {

    private final DailyWalkRepository dailyWalkRepository;
    private final DailyWalkMapper dailyWalkMapper;

    //TODO:  UserRepository로 수정 EntityManager로 확인하지 말 것 (인프라 누수)
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
        LocalDateTime end = start.plusDays(1);

        DailyWalk found = dailyWalkRepository
                .findByUserIdAndCreatedAtBetween(userId, start, end)
                .orElseThrow(DailyWalkNotFoundException::new);

        log.info("[DailyWalkService] 사용자의 해당 날짜 조회 성공 : id={}, userId={}, date={} ",
                found.getId(), userId, date);
        return found;
    }

    @Override
    @Transactional
    public DailyWalk createDailyWalk(@Valid DailyWalkCreateRequest req) {
        log.debug("[DailyWalkService] 생성 요청: userId={}, step={}, distanceKm={}, burnCalories={}, date={}",
                req.userId(), req.step(), req.distanceKm(), req.burnCalories(), req.date());

        if (entityManager.find(User.class, req.userId()) == null) {
            log.warn("[DailyWalkService] 생성 실패 - 사용자 없음: userId={}", req.userId());
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        User userRef = entityManager.getReference(User.class, req.userId());
        LocalDate date = (req.date() != null) ? req.date() : LocalDate.now();
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        // 이미 있는 데이터 있는지 확인
        Optional<DailyWalk> existing =
                dailyWalkRepository.findByUserIdAndCreatedAtBetween(req.userId(), startOfDay, endOfDay);

        if (existing.isPresent()) {
            DailyWalk walk = existing.get();
            walk.update(req.step(), req.distanceKm(), req.burnCalories());
            walk.validateInvariants();
            log.info("[DailyWalkService] 업데이트 완료: id={}, userId={}, createdAt={}",
                    walk.getId(), req.userId(), walk.getCreatedAt());
            return walk;
        }

        DailyWalk walk = dailyWalkMapper.toEntity(req, userRef, startOfDay);
        walk.validateInvariants();
        DailyWalk saved = dailyWalkRepository.save(walk);

        log.info("[DailyWalkService] 저장 완료: dailyWalkId={}, userId={}, createdAt={}",
                saved.getId(), userRef.getId(), saved.getCreatedAt());

        return saved;
    }

    @Override
    public void updateDailyWalkStep(
            @NotNull Long userId,
            @Valid DailyWalkStepUpdateRequest req) {

        log.debug("[DailyWalkService] 걸음수 수정 요청 userId={}, req={}", userId, req);

        LocalDateTime start = req.date().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        int updated = dailyWalkRepository.updateStepByUserIdAndDate(
                userId,
                start,
                end,
                req.step(),
                req.distanceKm(),
                req.burnCalories()
        );
        if (updated == 0) {
            log.warn("[DailyWalkService] 걸음수 수정 실패(대상 없음): userId={}, date={}", userId, req.date());
            throw new DailyWalkNotFoundException();
        }

        log.info("[DailyWalkService] 걸음수 수정 완료: userId={}, req={}", userId, req);
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
