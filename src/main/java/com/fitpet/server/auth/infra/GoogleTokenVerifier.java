package com.fitpet.server.auth.infra;

import com.fitpet.server.auth.domain.exception.OAuthInvalidTokenException;
import com.fitpet.server.auth.domain.exception.OAuthVerificationFailedException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleTokenVerifier {

    @Value("${oauth.google.client-id}")
    private String clientId;

    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final JacksonFactory jsonFactory = new JacksonFactory();

    public GoogleProfile verifyIdToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new OAuthInvalidTokenException();
            }

            Payload payload = idToken.getPayload();
            String sub = payload.getSubject();        // 구글 UID
            String email = payload.getEmail();        // 이메일(verify된 경우)

            return new GoogleProfile(sub, email);
        } catch (Exception e) {
            throw new OAuthVerificationFailedException();
        }
    }

    public record GoogleProfile(String sub, String email) {
    }
}