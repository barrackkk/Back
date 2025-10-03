package com.fitpet.server.dailyworkout.domain.repository;

import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsSessionRepository extends JpaRepository<GpsSession, Long> {
}