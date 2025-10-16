package com.fitpet.server.dailyworkout.Infra.jpa;

import com.fitpet.server.dailyworkout.domain.entity.GpsLog;
import com.fitpet.server.dailyworkout.domain.repository.GpsLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GpsLogRepositoryAdapter implements GpsLogRepository {

    private final GpsLogJpaRepository gpsLogJpaRepository;

    @Override
    public GpsLog save(GpsLog gpsLog) {
        return gpsLogJpaRepository.save(gpsLog);
    }
}