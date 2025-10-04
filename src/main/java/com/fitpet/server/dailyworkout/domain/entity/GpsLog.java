package com.fitpet.server.dailyworkout.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "gps_log")
public class GpsLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gps_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private GpsSession gpsSession;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude; // 위도

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude; // 경도

    @Column(precision = 5, scale = 2)
    private BigDecimal speed; // km/h

    @Column(precision = 6, scale = 2)
    private BigDecimal altitude; // m 단위 고도

    @Column(nullable = false)
    private LocalDateTime recordedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

}