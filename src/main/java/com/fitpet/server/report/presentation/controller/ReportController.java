package com.fitpet.server.report.presentation.controller;

import com.fitpet.server.meal.presentation.dto.response.MealDetailResponse;
import com.fitpet.server.report.application.service.ReportService;
import com.fitpet.server.report.presentation.dto.response.DailyMealSummaryResponse;
import com.fitpet.server.report.presentation.dto.response.MealCalendarResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.ActivityRangeResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.TodayActivityResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final Long testUserId = 3L;

    @GetMapping("/activity/daily")
    public ResponseEntity<TodayActivityResponse> getTodayActivity(
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        TodayActivityResponse response = reportService.getTodayActivity(testUserId, date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activity/range")
    public ResponseEntity<List<ActivityRangeResponse>> getActivityRange(
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<ActivityRangeResponse> response = reportService.getActivityRange(testUserId, from, to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meals")
    public ResponseEntity<List<MealDetailResponse>> getTodayMeals(
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {

        List<MealDetailResponse> response = reportService.getTodayMeals(testUserId, day);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meal/day")
    public ResponseEntity<DailyMealSummaryResponse> getDailyMealsReport(
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {

        DailyMealSummaryResponse response = reportService.getDailyMealsReport(testUserId, day);
        return ResponseEntity.ok(response);
    }

    // (수정) 월별 식단 캘린더 조회
    @GetMapping("/meal/calendar")
    public ResponseEntity<MealCalendarResponse> getMealCalendarReport(
            //@AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam int year,
            @RequestParam int month) {

        MealCalendarResponse response = reportService.getMealCalendarReport(testUserId, year, month);
        return ResponseEntity.ok(response);
    }
}