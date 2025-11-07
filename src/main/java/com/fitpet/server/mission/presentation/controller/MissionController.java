package com.fitpet.server.mission.presentation.controller;

import com.fitpet.server.mission.application.service.MissionCheckService;
import com.fitpet.server.mission.application.service.MissionService;
import com.fitpet.server.mission.presentation.dto.MissionCheckDto;
import com.fitpet.server.mission.presentation.dto.MissionCheckRequest;
import com.fitpet.server.mission.presentation.dto.MissionCreateRequest;
import com.fitpet.server.mission.presentation.dto.MissionDto;
import com.fitpet.server.mission.presentation.dto.MissionUpdateRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/missions")
@RequiredArgsConstructor
public class MissionController {

    private final MissionService missionService;
    private final MissionCheckService missionCheckService;

    @PostMapping
    public ResponseEntity<MissionDto> createMission(@Valid @RequestBody MissionCreateRequest request) {
        MissionDto created = missionService.createMission(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{missionId}")
                .buildAndExpand(created.missionId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<MissionDto>> getMissions() {
        return ResponseEntity.ok(missionService.getMissions());
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<MissionDto> getMission(@PathVariable Long missionId) {
        return ResponseEntity.ok(missionService.getMission(missionId));
    }

    @PatchMapping("/{missionId}")
    public ResponseEntity<MissionDto> updateMission(@PathVariable Long missionId,
                                                    @RequestBody MissionUpdateRequest request) {
        return ResponseEntity.ok(missionService.updateMission(missionId, request));
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long missionId) {
        missionService.deleteMission(missionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{missionId}/users/{userId}/checks")
    public ResponseEntity<MissionCheckDto> upsertMissionCheck(@PathVariable Long missionId,
                                                              @PathVariable Long userId,
                                                              @Valid @RequestBody MissionCheckRequest request) {
        MissionCheckDto response = missionCheckService.upsertMissionCheck(missionId, userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/checks")
    public ResponseEntity<List<MissionCheckDto>> getMissionChecks(@PathVariable Long userId) {
        return ResponseEntity.ok(missionCheckService.getMissionChecks(userId));
    }

    @DeleteMapping("/checks/{missionCheckId}")
    public ResponseEntity<Void> deleteMissionCheck(@PathVariable Long missionCheckId) {
        missionCheckService.deleteMissionCheck(missionCheckId);
        return ResponseEntity.noContent().build();
    }
}
