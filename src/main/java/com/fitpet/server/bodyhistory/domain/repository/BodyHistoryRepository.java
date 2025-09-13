package com.fitpet.server.bodyhistory.domain.repository;

import com.fitpet.server.bodyhistory.domain.entity.BodyHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BodyHistoryRepository {
    BodyHistory save(BodyHistory bodyHistory);

    Optional<BodyHistory> findById(Long id);

    List<BodyHistory> findAllByUserId(Long userId);

    List<BodyHistory> findAllByUserIdAndDate(Long userId, LocalDate startDate, LocalDate endDate);

    void deleteById(Long id);

    boolean existsById(Long id);
}
