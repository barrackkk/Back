package com.fitpet.server.dailywalk.infra.jpa;

import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import com.fitpet.server.dailywalk.domain.repository.DailyWalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DailyWalkRepositoryAdapter implements DailyWalkRepository {

    private final DailyWalkJpaRepository jpaRepository;

    @Override
    public List<DailyWalk> findAllByUserId(Long userId) {
        return jpaRepository.findAllByUser_Id(userId);
    }

    @Override
    public Optional<DailyWalk> findByUserIdAndDate(Long userId, LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByUser_IdAndCreatedAtBetween(userId, start, end);
    }

    @Override
    public DailyWalk save(DailyWalk dailyWalk) {
        return jpaRepository.save(dailyWalk);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public int updateStepByUserIdAndDate(Long userId, LocalDateTime start, LocalDateTime end, Integer step) {
        return jpaRepository.updateStepByUserIdAndDate(userId, start, end, step);
    }
}
