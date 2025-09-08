package com.fitpet.server.dailywalk.presentation.controller;

import com.fitpet.server.dailywalk.application.service.DailyWalkService;
import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.dailywalk.presentation.dto.request.DailyWalkStepUpdateRequest;
import com.fitpet.server.dailywalk.presentation.dto.response.DailyWalkResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/daily/walks")
@RequiredArgsConstructor
@Validated
public class DailyWalkController {

    private final DailyWalkService dailyWalkService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<DailyWalkResponse>> listByUser(@PathVariable @NotNull Long userId) {
        List<DailyWalkResponse> body = dailyWalkService.getAllByUserId(userId);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/users/{userId}/date")
    public ResponseEntity<DailyWalkResponse> getByUserAndDate(
            @PathVariable @NotNull Long userId,
            @RequestParam("date")
            @PastOrPresent
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        var dailyWalk = dailyWalkService.getDailyWalkByUserIdAndDate(userId, date);
        return ResponseEntity.ok(DailyWalkResponse.from(dailyWalk));
    }

    @PostMapping
    public ResponseEntity<DailyWalkResponse> create(@RequestBody @Valid DailyWalkCreateRequest req) {
        var saved = dailyWalkService.createDailyWalk(req);
        return ResponseEntity
                .created(URI.create("/daily/walks/" + saved.getId()))
                .body(DailyWalkResponse.from(saved));
    }

    @PatchMapping("/users/{userId}/step")
    public ResponseEntity<Void> updateStepByUserAndDate(@PathVariable @NotNull Long userId,
                                                        @RequestBody @Valid DailyWalkStepUpdateRequest req) {
        dailyWalkService.updateDailyWalkStep(userId, req);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{dailyWalkId}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long dailyWalkId) {
        dailyWalkService.deleteDailyWalk(dailyWalkId);
        return ResponseEntity.noContent().build();
    }
}