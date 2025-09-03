package com.fitpet.server.dailywalk.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fitpet.server.user.entity.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
        name = "daily_walk",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "created_at"})
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DailyWalk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_walk_id")
    private Long id;

    @Column(nullable = false)
    private Integer step;

    @Column(name = "distance_km", nullable = false, precision = 7, scale = 3)
    private BigDecimal distanceKm;

    @Column(name = "burn_calories", nullable = false)
    private Integer burnCalories;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public void update(int step, BigDecimal distanceKm, int burnCalories) {
        if (step < 0) throw new IllegalArgumentException("걸음 수는 음수일 수 없습니다.");
        if (distanceKm == null || distanceKm.signum() < 0) {
            throw new IllegalArgumentException("거리(km)는 0 이상이어야 합니다.");
        }
        if (burnCalories < 0) throw new IllegalArgumentException("소모 칼로리는 0 이상이어야 합니다.");

        this.step = step;
        this.distanceKm = distanceKm;
        this.burnCalories = burnCalories;
    }


    public void validateInvariants() {
        if (this.step == null || this.step < 0) {
            throw new IllegalArgumentException("걸음 수는 0 이상이어야 합니다.");
        }
        if (this.distanceKm == null || this.distanceKm.signum() < 0) {
            throw new IllegalArgumentException("거리(km)는 0 이상이어야 합니다.");
        }
        if (this.burnCalories == null || this.burnCalories < 0) {
            throw new IllegalArgumentException("소모 칼로리는 0 이상이어야 합니다.");
        }
    }
}

