package com.fitpet.server.report.application.service;

import com.fitpet.server.dailywalk.domain.repository.DailyWalkRepository;
import com.fitpet.server.dailyworkout.domain.entity.GpsSession;
import com.fitpet.server.dailyworkout.domain.repository.GpsSessionRepository;
import com.fitpet.server.meal.application.service.MealService;
import com.fitpet.server.meal.application.service.S3Service;
import com.fitpet.server.meal.domain.repository.MealRepository;
import com.fitpet.server.meal.presentation.dto.response.MealDetailResponse;
import com.fitpet.server.report.application.mapper.ReportMapper;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.ActivityRangeResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.TodayActivityResponse;
import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import java.math.BigDecimal;
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
    private final MealService mealService;
    private final MealRepository mealRepository;
    private final S3Service s3Service;

    private final ReportMapper reportMapper;

    @Override
    public TodayActivityResponse getTodayActivity(Long userId, LocalDate date) {
        User user = findUserById(userId);
        List<GpsSession> sessions = gpsSessionRepository.findByUserAndStartTimeBetween(user, date.atStartOfDay(),
                date.plusDays(1).atStartOfDay());

        BigDecimal totalDistance = sessions.stream()
                .map(GpsSession::getTotalDistance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int totalCalories = sessions.stream()
                .mapToInt(GpsSession::getBurnCalories)
                .sum();

        return TodayActivityResponse.builder()
                .date(date).distanceKm(totalDistance).burnCalories(totalCalories).build();
    }

    @Override
    public List<ActivityRangeResponse> getActivityRange(Long userId, LocalDate from, LocalDate to) {
        User user = findUserById(userId);
        return dailyWalkRepository.findByUserAndDateBetween(user, from, to).stream()
                .map(reportMapper::toActivityRangeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MealDetailResponse> getTodayMeals(Long userId, LocalDate date) {
        return mealService.getMealsByDate(userId, date);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}