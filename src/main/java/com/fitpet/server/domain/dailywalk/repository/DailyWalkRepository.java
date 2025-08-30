package com.fitpet.server.domain.dailywalk.repository;

import com.fitpet.server.domain.dailywalk.entity.DailyWalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyWalkRepository extends JpaRepository<DailyWalk, Long> {

    List<DailyWalk> findAllByUser_Id(Long userId);

    @Query("""
           select dw from DailyWalk dw
            where dw.user.id = :userId
              and dw.createdAt >= :start
              and dw.createdAt < :end
           """)
    Optional<DailyWalk> findByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           update DailyWalk dw
              set dw.step = :step
            where dw.user.id = :userId
              and dw.createdAt >= :start
              and dw.createdAt < :end
           """)
    int updateStepByUserIdAndDate(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("step") Integer step
    );
}
