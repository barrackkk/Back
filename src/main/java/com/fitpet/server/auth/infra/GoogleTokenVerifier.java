package com.fitpet.server.auth.infra;

import com.fitpet.server.auth.domain.exception.OAuthInvalidTokenException;
import com.fitpet.server.auth.domain.exception.OAuthVerificationFailedException;
import com.fitpet.server.auth.presentation.dto.GoogleProfile;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleTokenVerifier {

    @Value("${oauth.google.client-id}")
    private String clientId;

    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final JacksonFactory jsonFactory = new JacksonFactory();

    // Google이 문서에 명시하는 유효 발급자
    private static final Set<String> ALLOWED_ISSUERS = Set.of(
        "https://accounts.google.com", "accounts.google.com"
    );

    public GoogleProfile verifyIdToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId)) // aud 검증
                .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new OAuthInvalidTokenException();
            }

            Payload payload = idToken.getPayload();

            // iss(발급자) 검증
            String iss = payload.getIssuer();
            if (!ALLOWED_ISSUERS.contains(iss)) {
                throw new OAuthVerificationFailedException();
            }

            // 이메일/검증여부 추출
            String email = payload.getEmail(); // null 가능
            Boolean emailVerified = (Boolean) payload.get("email_verified"); // null일 수도 있음
            boolean verified = emailVerified != null && emailVerified;

            String sub = payload.getSubject(); // 고유 사용자 ID

            return new GoogleProfile(sub, email, verified);
        } catch (OAuthInvalidTokenException | OAuthVerificationFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new OAuthVerificationFailedException();
        }
    }

}