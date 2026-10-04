package com.attendance.face.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_records")
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    private String status; // PRESENT, LATE, EARLY_EXIT
    private Double confidenceScore;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String capturedSnapshot; // Base64 snapshot taken during check-in

    public AttendanceRecord() {}

    public AttendanceRecord(User user, LocalDate date, LocalTime checkInTime, String status, Double confidenceScore, String capturedSnapshot) {
        this.user = user;
        this.date = date;
        this.checkInTime = checkInTime;
        this.status = status;
        this.confidenceScore = confidenceScore;
        this.capturedSnapshot = capturedSnapshot;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalTime checkInTime) { this.checkInTime = checkInTime; }

    public LocalTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalTime checkOutTime) { this.checkOutTime = checkOutTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }

    public String getCapturedSnapshot() { return capturedSnapshot; }
    public void setCapturedSnapshot(String capturedSnapshot) { this.capturedSnapshot = capturedSnapshot; }
}
