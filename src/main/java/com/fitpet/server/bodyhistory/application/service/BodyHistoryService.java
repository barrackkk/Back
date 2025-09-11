package com.fitpet.server.bodyhistory.application.service;


import com.fitpet.server.bodyhistory.domain.entity.BodyHistory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;

@Validated
public interface BodyHistoryService {
    BodyHistory save(BodyHistory bodyHistory);

    Optional<BodyHistory> findById(Long id);

    List<BodyHistory> findAllByUserId(Long userId);

    // Date는 월 단위로 검색
    List<BodyHistory> findAllByUserIdAndDate(Long userId, LocalDate startDate, LocalDate endDate);

    void deleteById(Long id);
}
