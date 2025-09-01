package com.fitpet.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Postman 테스트 편하게 하기위해 일단 끔
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/**").permitAll() // 회원가입/조회 허용
                .requestMatchers("/daily/walks/**").permitAll() // 하루 걸음수 관련 허용
                .requestMatchers("/actuator/**").permitAll() // health, info 등 actuator 공개
                .anyRequest().authenticated()                 // 나머지는 인증 필요
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}