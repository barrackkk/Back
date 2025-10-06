package com.fitpet.server.dailyworkout.domain.repository;

import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import java.util.Optional;


public interface GpsSessionRepository {
    GpsSession save(GpsSession gpsSession);

    Optional<GpsSession> findById(Long id);
}