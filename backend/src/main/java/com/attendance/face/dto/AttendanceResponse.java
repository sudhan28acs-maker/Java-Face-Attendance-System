package com.attendance.face.dto;

import com.attendance.face.entity.AttendanceRecord;
import com.attendance.face.entity.User;

public class AttendanceResponse {
    private boolean success;
    private String message;
    private User user;
    private AttendanceRecord record;
    private Double matchDistance;

    public AttendanceResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AttendanceResponse(boolean success, String message, User user, AttendanceRecord record, Double matchDistance) {
        this.success = success;
        this.message = message;
        this.user = user;
        this.record = record;
        this.matchDistance = matchDistance;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public AttendanceRecord getRecord() { return record; }
    public void setRecord(AttendanceRecord record) { this.record = record; }

    public Double getMatchDistance() { return matchDistance; }
    public void setMatchDistance(Double matchDistance) { this.matchDistance = matchDistance; }
}
