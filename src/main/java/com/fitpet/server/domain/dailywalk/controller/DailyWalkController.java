package com.fitpet.server.domain.dailywalk.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

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

import com.fitpet.server.domain.dailywalk.dto.request.DailyWalkCreateRequest;
import com.fitpet.server.domain.dailywalk.dto.request.DailyWalkStepUpdateRequest;
import com.fitpet.server.domain.dailywalk.dto.response.DailyWalkResponse;
import com.fitpet.server.domain.dailywalk.service.DailyWalkService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;


// import 동일

@RestController
@RequestMapping("/daily/walks")
@RequiredArgsConstructor
@Validated
public class DailyWalkController {

    private final DailyWalkService dailyWalkService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<DailyWalkResponse>> getAllByUser(@PathVariable @NotNull Long userId) {
        var body = dailyWalkService.getDailyWalksByUser(userId)
                .stream().map(DailyWalkResponse::from).toList();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/users/{userId}/by-date")
    public ResponseEntity<DailyWalkResponse> getByUserAndDate(
            @PathVariable @NotNull Long userId,
            @RequestParam("createdAt")
            @PastOrPresent
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt
    ) {
        var dw = dailyWalkService.getDailyWalkByUserAndCreatedAt(userId, createdAt);
        return ResponseEntity.ok(DailyWalkResponse.from(dw));
    }

    @PostMapping
    public ResponseEntity<DailyWalkResponse> create(@RequestBody @Valid DailyWalkCreateRequest req) {
        var saved = dailyWalkService.createDailyWalk(req);
        return ResponseEntity
                .created(URI.create("/daily/walks/" + saved.getId()))
                .body(DailyWalkResponse.from(saved));
    }

    @PatchMapping("/users/{userId}/step")
    public ResponseEntity<Void> updateStep(@PathVariable @NotNull Long userId,
                                           @RequestBody @Valid DailyWalkStepUpdateRequest req) {
        dailyWalkService.updateDailyWalkStep(userId, req.createdAt(), req.step());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{dailyWalkId}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long dailyWalkId) {
        dailyWalkService.deleteDailyWalk(dailyWalkId);
        return ResponseEntity.noContent().build();
    }
}