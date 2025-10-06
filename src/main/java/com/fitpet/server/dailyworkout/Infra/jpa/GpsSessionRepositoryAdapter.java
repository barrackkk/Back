package com.fitpet.server.dailyworkout.infra.jpa;

import com.fitpet.server.dailyworkout.Infra.jpa.GpsSessionJpaRepository;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.dailyworkout.domain.repository.GpsSessionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GpsSessionRepositoryAdapter implements GpsSessionRepository {

    private final GpsSessionJpaRepository gpsSessionJpaRepository;

    @Override
    public GpsSession save(GpsSession gpsSession) {
        return gpsSessionJpaRepository.save(gpsSession);
    }

    @Override
    public Optional<GpsSession> findById(Long id) {
        return gpsSessionJpaRepository.findById(id);
    }
}