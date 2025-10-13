package com.fitpet.server.dailyworkout.Infra.jpa;

import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GpsSessionJpaRepository extends JpaRepository<GpsSession, Long> {
}