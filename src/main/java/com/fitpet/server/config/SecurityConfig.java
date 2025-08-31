package com.fitpet.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Postman 테스트 편하게 하기위해 일단 끔
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/**").permitAll() // 회원가입/조회 허용
                .anyRequest().authenticated()                 // 나머지는 인증 필요
            );
        return http.build();
    }
}