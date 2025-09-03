package com.fitpet.server.dailywalk.infra.jpa;

import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyWalkJpaRepository extends JpaRepository<DailyWalk, Long> {
    List<DailyWalk> findAllByUser_Id(Long userId);

    Optional<DailyWalk> findFirstByUser_IdAndCreatedAtBetweenOrderByCreatedAtDesc(
            Long userId, LocalDateTime start, LocalDateTime end);

    Optional<DailyWalk> findByUser_IdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update DailyWalk dw
              set dw.step = :step
            where dw.user.id = :userId
              and dw.createdAt >= :start
              and dw.createdAt < :end
           """)
    int updateStepByUserIdAndDate(@Param("userId") Long userId,
                                  @Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end,
                                  @Param("step") Integer step);
}
