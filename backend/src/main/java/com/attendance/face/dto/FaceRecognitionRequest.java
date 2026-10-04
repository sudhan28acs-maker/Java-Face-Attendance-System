package com.attendance.face.dto;

public class FaceRecognitionRequest {
    private String employeeId;
    private String faceDescriptor;     // JSON array string e.g. "[0.123, -0.045, ...]"
    private String capturedSnapshot;   // Base64 snapshot image
    private String actionType;         // "CHECK_IN" or "CHECK_OUT"
    private Boolean livenessVerified;  // True if anti-spoofing / eye blink passed
    private Integer blinkCount;        // Number of blinks recorded
    private Double earScore;           // Eye Aspect Ratio score

    public FaceRecognitionRequest() {}

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFaceDescriptor() { return faceDescriptor; }
    public void setFaceDescriptor(String faceDescriptor) { this.faceDescriptor = faceDescriptor; }

    public String getCapturedSnapshot() { return capturedSnapshot; }
    public void setCapturedSnapshot(String capturedSnapshot) { this.capturedSnapshot = capturedSnapshot; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }

    public Boolean getLivenessVerified() { return livenessVerified; }
    public void setLivenessVerified(Boolean livenessVerified) { this.livenessVerified = livenessVerified; }

    public Integer getBlinkCount() { return blinkCount; }
    public void setBlinkCount(Integer blinkCount) { this.blinkCount = blinkCount; }

    public Double getEarScore() { return earScore; }
    public void setEarScore(Double earScore) { this.earScore = earScore; }
}
