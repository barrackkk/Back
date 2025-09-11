package com.fitpet.server.bodyhistory.infra.jpa;

import com.fitpet.server.bodyhistory.domain.entity.BodyHistory;
import com.fitpet.server.bodyhistory.domain.repository.BodyHistoryRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class BodyHistoryAdapter implements BodyHistoryRepository {

    private final BodyHistoryJpaRepository jpaRepository;

    @Override
    public BodyHistory save(BodyHistory bodyHistory) {
        return jpaRepository.save(bodyHistory);
    }

    @Override
    public Optional<BodyHistory> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<BodyHistory> findAllByUserId(Long userId) {
        return jpaRepository.findAllByUserId(userId);
    }

    @Override
    public List<BodyHistory> findAllByUserIdAndDate(Long userId, LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findAllByUserIdAndDate(userId, startDate, endDate);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
