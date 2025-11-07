package com.fitpet.server.alram.application.service;

import com.fitpet.server.alram.domain.entity.AlramMessage;
import com.fitpet.server.alram.domain.repository.AlramRepository;
import com.fitpet.server.alram.presentation.dto.request.AlramRequestDto;
import com.fitpet.server.alram.presentation.dto.response.AlramResponseDto;
import com.fitpet.server.shared.exception.BusinessException;
import com.fitpet.server.shared.exception.ErrorCode;
import com.fitpet.server.user.domain.entity.User;
import com.fitpet.server.user.domain.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlramServiceImpl implements AlramService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;
    private final AlramRepository alramRepository;

    @Override
    @Transactional
    public AlramResponseDto sendAndSaveAlram(AlramRequestDto requestDto) {

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();
        Message fcmMessage = Message.builder()
                .setToken(requestDto.getTargetDeviceToken())
                .setNotification(notification)
                .build();

        String fcmMessageId;
        try {
            fcmMessageId = firebaseMessaging.send(fcmMessage);
            log.info("FCM 알림 발송 성공: {}", fcmMessageId);
        } catch (FirebaseMessagingException e) {
            log.error("FCM 알림 발송 실패: {}", e.getMessage(), e);
            // TODO: FCM_SEND_FAILED와 같은 ErrorCode를 정의하여 BusinessException 처리
            throw new RuntimeException("FCM 알림 발송에 실패했습니다.", e);
        }

        AlramMessage alramToSave = AlramMessage.builder()
                .user(user)
                .title(requestDto.getTitle())
                .message(requestDto.getBody())
                .build();

        AlramMessage savedAlram = alramRepository.save(alramToSave);

        return new AlramResponseDto(savedAlram.getId(), fcmMessageId, "알림 발송 및 저장 성공");
    }
}