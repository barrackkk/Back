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
    public MissionDto getMission(Long missionId) {
        Mission mission = findMission(missionId);
        return missionMapper.toDto(mission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissionDto> getMissions() {
        return missionMapper.toDtos(missionRepository.findAll());
    }

    @Override
    @Transactional
    public MissionDto updateMission(Long missionId, MissionUpdateRequest request) {
        Mission mission = findMission(missionId);
        mission.update(request.title(), request.content(), request.type(), request.goal());
        log.info("[MissionService] 미션 수정: missionId={}", missionId);
        return missionMapper.toDto(mission);
    }

    @Override
    public void deleteMission(Long missionId) {
        Mission mission = findMission(missionId);
        missionRepository.delete(mission);
        log.info("[MissionService] 미션 삭제: missionId={}", missionId);
    }

    private Mission findMission(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(MissionNotFoundException::new);
    }
}
