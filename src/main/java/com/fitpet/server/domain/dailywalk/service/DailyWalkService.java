package com.fitpet.server.domain.dailywalk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.fitpet.server.domain.dailywalk.entity.DailyWalk;

public interface DailyWalkService { 
    //TODO: Mapper 추가하기

    // 특정 사용자 전체 기록 조회
    List<DailyWalk> getDailyWalksByUser(Long userId);

    // 특정 사용자 특정 날짜 기록 조회
    Optional<DailyWalk> getDailyWalkByUserAndDate(Long userId, LocalDateTime date);

    // 새 기록 생성
    DailyWalk createDailyWalk(DailyWalk dailyWalk);

    // 기록 삭제
    void deleteDailyWalk(Long dailyWalkId);

    // 걸음 수 업데이트
    void updateDailyWalkStep(Long dailyWalkId, LocalDateTime date, Integer newStep);
}
