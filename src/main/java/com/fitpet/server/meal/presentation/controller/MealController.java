package com.fitpet.server.meal.presentation.controller;

import com.fitpet.server.meal.application.service.MealService;
import com.fitpet.server.meal.presentation.dto.MealDto.CreateRequest;
import com.fitpet.server.meal.presentation.dto.MealDto.CreateResponse;
import com.fitpet.server.meal.presentation.dto.MealDto.MealResponse;
import com.fitpet.server.meal.presentation.dto.MealDto.UpdateRequest;
import com.fitpet.server.meal.presentation.dto.MealDto.UpdateResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meal")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final Long userId = 3L; // 임시 UserId

    @GetMapping
    public ResponseEntity<List<MealResponse>> getMealsByDate(
            // @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<MealResponse> response = mealService.getMealsByDate(userId, date);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateResponse> createMeal(
            @Valid @RequestBody CreateRequest request) {
        CreateResponse response = mealService.createMeal(userId, request);
        return ResponseEntity.ok(response);
    }
햣
    @PutMapping("/{mealId}")
    public ResponseEntity<UpdateResponse> updateMeal(
            @PathVariable Long mealId,
            @Valid @RequestBody UpdateRequest request) {
        UpdateResponse response = mealService.updateMeal(userId, mealId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(
            @PathVariable Long mealId) {
        mealService.deleteMeal(userId, mealId);
        return ResponseEntity.noContent().build();
    }
}