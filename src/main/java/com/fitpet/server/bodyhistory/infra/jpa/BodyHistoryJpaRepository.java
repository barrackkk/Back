package com.fitpet.server.bodyhistory.infra.jpa;

import com.fitpet.server.bodyhistory.domain.entity.BodyHistory;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BodyHistoryJpaRepository extends JpaRepository<BodyHistory, Long> {

    List<BodyHistory> findAllByUserId(Long userId);

    @Query("""
            SELECT bh
            FROM BodyHistory bh
            WHERE bh.user.id = :userId
            AND bh.baseDate BETWEEN :startDate AND :endDate
            """)
    List<BodyHistory> findAllByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}