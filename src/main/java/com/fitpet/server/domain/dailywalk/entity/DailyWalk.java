package com.fitpet.server.domain.dailywalk.entity;

import java.time.LocalDateTime;

import com.fitpet.server.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "daily_walk")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyWalk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_walk_id")
    private Long Id;  // BIGINT AUTO_INCREMENT

    @Column(nullable = false)
    private Integer step;  // 걸음 수

    @Column(name = "distance_km", nullable = false)
    private Double distanceKm; 

    @Column(name = "burn_calories", nullable = false)
    private Integer burnCalories;  

    @Column(name = "created_at", nullable = false,
            updatable = false,
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // User와 N:1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
