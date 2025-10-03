package com.fitpet.server.dailyworkout.domain.repository;

import com.fitpet.server.dailyworkout.domain.entity.GpsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsLogRepository extends JpaRepository<GpsLog, Long> {
}