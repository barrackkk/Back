package com.fitpet.server.report.presentation.controller;

import com.fitpet.server.report.application.service.ReportService;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.DailyWalkSummaryResponse;
import com.fitpet.server.report.presentation.dto.response.ReportResponseDto.TodayWorkoutResponse;
import com.fitpet.server.shared.security.UserDetailsImpl;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/today-workout")
    public ResponseEntity<List<TodayWorkoutResponse>> getTodayWorkouts(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<TodayWorkoutResponse> response = reportService.getTodayWorkouts(userDetails.getUserId(), date);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activity/range")
    public ResponseEntity<List<DailyWalkSummaryResponse>> getWeeklyWalks(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        List<DailyWalkSummaryResponse> response = reportService.getWeeklyWalks(userDetails.getUserId(), from, to);
        return ResponseEntity.ok(response);
    }
}