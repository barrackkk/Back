package com.fitpet.server.domain.dailywalk.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitpet.server.domain.dailywalk.entity.DailyWalk;

@Repository
public interface DailyWalkRepository extends JpaRepository<DailyWalk, Long> {

    // 특정 사용자 전체 기록 조회
    List<DailyWalk> findByUserId(Long userId);

    // 특정 사용자 + 날짜 범위 조회 (예: 한 달치)
    List<DailyWalk> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    // 특정 사용자 하루 기록 조회 (날짜 기준)
    Optional<DailyWalk> findByUserIdAndCreatedAt(Long userId, LocalDateTime recordedAt);

    // 특정 사용자 하루 기록 존재 여부 (오늘 row 생성용)
    boolean existsByUserIdAndCreatedAt(Long userId, LocalDateTime createdAt);
}
