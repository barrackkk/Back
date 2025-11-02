package com.fitpet.server.dailyworkout.Infra.jpa;

import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.user.domain.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GpsSessionJpaRepository extends JpaRepository<GpsSession, Long> {
    List<GpsSession> findByUserAndStartTimeBetween(User user, LocalDateTime start, LocalDateTime end);
    
}