package com.fitpet.server.auth.infra;

import com.fitpet.server.auth.domain.exception.OAuthProfileNotFoundException;
import com.fitpet.server.auth.domain.exception.OAuthVerificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    private final WebClient webClient = WebClient.builder().build();

    @Value("${oauth.kakao.user-info-uri}")
    private String userInfoUri;

    public KakaoProfile getProfile(String accessToken) {
        KakaoMeResponse response = webClient.get()
            .uri(userInfoUri)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .retrieve()
            .bodyToMono(KakaoMeResponse.class)
            .onErrorResume(e -> Mono.error(new OAuthVerificationFailedException()))
            .block();

        if (response == null || response.id() == null) {
            throw new OAuthProfileNotFoundException();
        }
        String email = response.kakao_account() != null ? response.kakao_account().email() : null;
        return new KakaoProfile(String.valueOf(response.id()), email);
    }

    // 매핑 역할들
    public record KakaoProfile(String id, String email) {
    }

    public record KakaoMeResponse(Long id, KakaoAccount kakao_account) {
    }

    public record KakaoAccount(String email) {
    }
}