package com.fitpet.server.domain.dailywalk.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitpet.server.domain.dailywalk.entity.DailyWalk;

@Repository
public interface DailyWalkRepository extends JpaRepository<DailyWalk, Long> {

    List<DailyWalk> findAllByUser_Id(Long userId);

    List<DailyWalk> findByUser_IdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    Optional<DailyWalk> findByUser_IdAndCreatedAt(Long userId, LocalDateTime createdAt);

    boolean existsByUser_IdAndCreatedAt(Long userId, LocalDateTime createdAt);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           UPDATE DailyWalk dw
              SET dw.step = :step
            WHERE dw.user.id = :userId
              AND dw.createdAt = :createdAt
           """)
    int updateStepByUserIdAndCreatedAt(@Param("userId") Long userId,
                                       @Param("createdAt") LocalDateTime createdAt,
                                       @Param("step") Integer step);
}
