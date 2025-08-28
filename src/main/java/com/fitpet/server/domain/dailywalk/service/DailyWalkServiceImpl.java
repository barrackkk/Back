package com.fitpet.server.domain.dailywalk.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fitpet.server.domain.dailywalk.entity.DailyWalk;
import com.fitpet.server.domain.dailywalk.exception.DailyWalkNotFoundException;
import com.fitpet.server.domain.dailywalk.repository.DailyWalkRepository;

import jakarta.transaction.Transactional;

@Service
public class DailyWalkServiceImpl implements DailyWalkService {

    private final DailyWalkRepository dailyWalkRepository;

    public DailyWalkServiceImpl(DailyWalkRepository dailyWalkRepository) {
        this.dailyWalkRepository = dailyWalkRepository;
    }

    @Override
    public List<DailyWalk> getDailyWalksByUser(Long userId) {
        return dailyWalkRepository.findAllByUserId(userId);
    }    

    @Override
    public Optional<DailyWalk> getDailyWalkByUserAndDate(Long userId, LocalDateTime date) {
        return dailyWalkRepository.findByUserIdAndCreatedAt(userId, date);
    }

    @Override
    public DailyWalk createDailyWalk(DailyWalk dailyWalk) {
        return dailyWalkRepository.save(dailyWalk);
    }

    @Override
    public void deleteDailyWalk(Long dailyWalkId) {
        dailyWalkRepository.deleteById(dailyWalkId);
    }

    @Override
    @Transactional
    public void updateDailyWalkStep(Long userId, LocalDateTime date, Integer newStep) {
        // update 쿼리 실행
        int updated = dailyWalkRepository.updateStepByUserIdAndDate(userId, date, newStep);
        
        if (updated == 0) {
            throw new DailyWalkNotFoundException(
                "해당 날짜(" + date + ")의 사용자(" + userId + ") 기록을 찾을 수 없습니다."
            );
        }

        //TODO: 반환값 필요하면 여기서 다시 조회
    }
    
}
