package com.fitpet.server.user.domain.entity;


import com.fitpet.server.user.presentation.dto.UserUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email")
    })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(name = "nick_name", length = 60)
    private String nickname;

    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "target_weight_kg")
    private Double targetWeightKg;

    @Column(name = "height_cm")
    private Double heightCm;

    @Column
    private Double pbf;  // 체지방률

    @Column(name = "target_pbf")
    private Double targetPbf;

    @Column(length = 50)
    private String provider; // OAuth provider

    @Column(name = "device_token", length = 255)
    private String deviceToken;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;


    @CreatedDate
    @Column(name = "created_at", updatable = false,
        columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at",
        columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;


    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    // 사용자가 입력을 하지 않은 경우(빈값) 대비
    public void update(UserUpdateRequest request) {
        if (request.email() != null) {
            this.email = request.email();
        }
        if (request.nickname() != null) {
            this.nickname = request.nickname();
        }
        if (request.age() != null) {
            this.age = request.age();
        }
        if (request.gender() != null) {
            this.gender = request.gender();
        }
        if (request.weightKg() != null) {
            this.weightKg = request.weightKg();
        }
        if (request.targetWeightKg() != null) {
            this.targetWeightKg = request.targetWeightKg();
        }
        if (request.heightCm() != null) {
            this.heightCm = request.heightCm();
        }
        if (request.pbf() != null) {
            this.pbf = request.pbf();
        }
        if (request.targetPbf() != null) {
            this.targetPbf = request.targetPbf();
        }
    }
}