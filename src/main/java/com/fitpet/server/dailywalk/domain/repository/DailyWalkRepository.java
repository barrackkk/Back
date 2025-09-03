package com.fitpet.server.dailywalk.domain.repository;

import com.fitpet.server.dailywalk.domain.entity.DailyWalk;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyWalkRepository {
    List<DailyWalk> findAllByUserId(Long userId);

    Optional<DailyWalk> findByUserIdAndDate(Long userId, LocalDateTime start, LocalDateTime end);


    DailyWalk save(DailyWalk dailyWalk);

    boolean existsById(Long id);

    void deleteById(Long id);

    int updateStepByUserIdAndDate(Long userId, LocalDateTime start, LocalDateTime end, Integer step, BigDecimal distance);
}
