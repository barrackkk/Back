package com.fitpet.server.meal.presentation.controller;

import com.fitpet.server.meal.application.service.MealService;
import com.fitpet.server.meal.presentation.dto.request.MealCreateRequest;
import com.fitpet.server.meal.presentation.dto.request.MealUpdateRequest;
import com.fitpet.server.meal.presentation.dto.response.MealCreateResponse;
import com.fitpet.server.meal.presentation.dto.response.MealDetailResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final Long userId = 3L;

    @GetMapping
    public ResponseEntity<List<MealDetailResponse>> getMealsByDate(
            // @AuthenticationPrincipal UserDetailsImpl userDetails, // 구현 후 활성화
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {

        Long tempUserId = 3L;
        List<MealDetailResponse> response = mealService.getMealsByDate(tempUserId, day);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MealCreateResponse> createMeal(
            @Valid @RequestBody MealCreateRequest request) {
        MealCreateResponse response = mealService.createMeal(userId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{mealId}")
    public ResponseEntity<Object> updateMeal(
            @PathVariable Long mealId,
            @Valid @RequestBody MealUpdateRequest request) {

        Object response = mealService.updateMeal(userId, mealId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(
            @PathVariable Long mealId) {
        mealService.deleteMeal(userId, mealId);
        return ResponseEntity.noContent().build();
    }
}