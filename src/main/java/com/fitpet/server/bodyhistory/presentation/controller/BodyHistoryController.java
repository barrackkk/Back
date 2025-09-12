package com.fitpet.server.bodyhistory.presentation.controller;


import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fitpet.server.bodyhistory.application.service.BodyHistoryService;
import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryCreateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.request.BodyHistoryUpdateRequest;
import com.fitpet.server.bodyhistory.presentation.dto.response.BodyHistoryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("body-histories")
@RequiredArgsConstructor
@Validated
public class BodyHistoryController {
    private final BodyHistoryService bodyHistoryService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BodyHistoryResponse>> listByUser(@PathVariable Long userId) {
        List<BodyHistoryResponse> body = bodyHistoryService.findAllBodyHistoriesByUserId(userId);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/users/{userId}/date")
    public ResponseEntity<List<BodyHistoryResponse>> listByUserId(@PathVariable Long userId) {
        List<BodyHistoryResponse> body = bodyHistoryService.findAllBodyHistoriesByUserId(userId);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<BodyHistoryResponse> create(@RequestBody @Valid BodyHistoryCreateRequest request) {
        BodyHistoryResponse saved = bodyHistoryService.createBodyHistory(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<BodyHistoryResponse> update(
            @PathVariable Long userId,
            @RequestBody @Valid BodyHistoryUpdateRequest request) {
        bodyHistoryService.updateBodyHistory(userId, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> delete(@PathVariable Long historyId) {
        bodyHistoryService.deleteBodyHistory(historyId);
        return ResponseEntity.noContent().build();
    }

}
