package com.fitpet.server.report.application.service;

import com.fitpet.server.dailywalk.domain.entity.DailyWalk;
import com.fitpet.server.dailywalk.domain.repository.DailyWalkRepository;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.dailyworkout.domain.repository.GpsSessionRepository;
import com.fitpet.server.report.application.mapper.ReportMapper;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.DailyWalkSummaryResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.TodayWorkoutResponse;
import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepository;
    private final GpsSessionRepository gpsSessionRepository;
    private final DailyWalkRepository dailyWalkRepository;
    private final ReportMapper reportMapper;

    @Override
    public List<TodayWorkoutResponse> getTodayWorkouts(Long userId, LocalDate date) {
        User user = findUserById(userId);
        List<GpsSession> sessions = gpsSessionRepository.findByUserAndStartTimeBetween(user, date.atStartOfDay(),
                date.plusDays(1).atStartOfDay());

        // 수동 빌더 대신 매퍼를 사용하도록 수정
        return sessions.stream()
                .map(reportMapper::toTodayWorkoutResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DailyWalkSummaryResponse> getWeeklyWalks(Long userId) {
        User user = findUserById(userId);
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        List<DailyWalk> walks = dailyWalkRepository.findByUserAndDateBetween(user, sevenDaysAgo, today);

        // 수동 빌더 대신 매퍼를 사용하도록 수정
        return walks.stream()
                .map(reportMapper::toDailyWalkSummaryResponse)
                .collect(Collectors.toList());
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}