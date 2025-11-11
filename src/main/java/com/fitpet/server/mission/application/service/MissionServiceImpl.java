package com.fitpet.server.mission.application.service;

import com.fitpet.server.mission.application.mapper.MissionMapper;
import com.fitpet.server.mission.domain.entity.Mission;
import com.fitpet.server.mission.domain.exception.MissionNotFoundException;
import com.fitpet.server.mission.domain.repository.MissionRepository;
import com.fitpet.server.mission.presentation.dto.MissionCreateRequest;
import com.fitpet.server.mission.presentation.dto.MissionDto;
import com.fitpet.server.mission.presentation.dto.MissionUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final MissionMapper missionMapper;

    @Override
    public MissionDto createMission(MissionCreateRequest request) {
        Mission mission = missionMapper.toEntity(request);
        Mission saved = missionRepository.save(mission);
        log.info("[MissionService] 미션 생성: missionId={}", saved.getId());
        return missionMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissionDto> getMissions() {
        return missionMapper.toDtos(missionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public MissionDto getMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(MissionNotFoundException::new);
        return missionMapper.toDto(mission);
    }

    @Override
    public MissionDto updateMission(Long missionId, MissionUpdateRequest request) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(MissionNotFoundException::new);
        missionMapper.updateFromRequest(request, mission);
        Mission updated = missionRepository.save(mission);
        log.info("[MissionService] 미션 수정: missionId={}", updated.getId());
        return missionMapper.toDto(updated);
    }

    @Override
    public void deleteMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(MissionNotFoundException::new);
        missionRepository.delete(mission);
        log.info("[MissionService] 미션 삭제: missionId={}", missionId);
    }
}
