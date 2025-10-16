package com.fitpet.server.dailyworkout.domain.repository;

import com.fitpet.server.dailyworkout.domain.entity.GpsLog;

public interface GpsLogRepository {
    GpsLog save(GpsLog gpsLog);
}