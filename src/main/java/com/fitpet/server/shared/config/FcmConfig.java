package com.fitpet.server.shared.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class FcmConfig {

    @Value("${fcm.service-account-key-path}")
    private String serviceAccountKeyPath;

    private FirebaseApp firebaseApp;

    @PostConstruct
    public void initialize() {
        try {
            ClassPathResource resource = new ClassPathResource(serviceAccountKeyPath);

            try (InputStream serviceAccountStream = resource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                        .build();

                if (FirebaseApp.getApps().isEmpty()) {
                    this.firebaseApp = FirebaseApp.initializeApp(options);
                    log.info("FirebaseApp 초기화 성공");
                } else {
                    this.firebaseApp = FirebaseApp.getInstance();
                    log.info("FirebaseApp 이미 초기화됨");
                }
            }
        } catch (IOException e) {
            log.error("FCM 키 파일을 찾을 수 없거나 읽는 데 실패했습니다. (경로: {}): {}", serviceAccountKeyPath, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        if (this.firebaseApp == null) {
            throw new IllegalStateException("FirebaseApp이 초기화되어야 합니다.");
        }
        return FirebaseMessaging.getInstance(this.firebaseApp);
    }
}