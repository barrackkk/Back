package com.fitpet.server.dailyworkout.domain.entity;

import com.fitpet.server.user.domain.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gps_session")
public class GpsSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "gpsSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GpsLog> gpsLogs = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(precision = 8, scale = 2)
    private BigDecimal totalDistance;

    @Column(precision = 5, scale = 2)
    private BigDecimal avgSpeed;

    private Integer stepCount;

    private Integer burnCalories;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public GpsSession(User user, LocalDateTime startTime) {
        this.user = user;
        this.startTime = startTime;
    }

    //== 연관관계 편의 메서드 ==//
    public void addGpsLog(GpsLog gpsLog) {
        gpsLogs.add(gpsLog);
        gpsLog.setGpsSession(this);
    }

    //== 비즈니스 로직 ==//
    public void endSession(LocalDateTime endTime, BigDecimal totalDistance, BigDecimal avgSpeed, int stepCount,
                           int burnCalories) {
        this.endTime = endTime;
        this.totalDistance = totalDistance;
        this.avgSpeed = avgSpeed;
        this.stepCount = stepCount;
        this.burnCalories = burnCalories;
    }
}