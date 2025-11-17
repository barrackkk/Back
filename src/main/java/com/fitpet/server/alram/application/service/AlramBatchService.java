package com.fitpet.server.alram.application.service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlramBatchService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendMulticastNotification(List<String> tokens, String title, String body, Map<String, String> data) {
        if (tokens == null || tokens.isEmpty()) {
            log.warn("[FCM 멀티캐스트] 전송 대상 토큰이 없습니다.");
            return;
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(tokens)
                .putAllData(data != null ? data : Map.of())
                .build();

        try {
            BatchResponse response = firebaseMessaging.sendEachForMulticast(message);
            log.info("[FCM v1 멀티캐스트] 알림 전송 성공: {}/{}건", response.getSuccessCount(), tokens.size());

            if (response.getFailureCount() > 0) {
                List<String> failedTokens = new ArrayList<>();
                List<SendResponse> responses = response.getResponses();

                for (int i = 0; i < responses.size(); i++) {
                    if (!responses.get(i).isSuccessful()) {
                        String failedToken = tokens.get(i);
                        failedTokens.add(failedToken);
                        log.warn("[FCM v1 멀티캐스트] 전송 실패: 토큰={}, 에러={}",
                                failedToken, responses.get(i).getException().getMessage());
                    }
                }
                log.warn("[FCM v1 멀티캐스트] 총 {}건 실패. 실패 토큰 목록: {}", response.getFailureCount(), failedTokens);
            }

        } catch (FirebaseMessagingException e) {
            log.error("[FCM v1 멀티캐스트] 전송 중 심각한 오류 발생: {}", e.getMessage(), e);
        }
    }
}