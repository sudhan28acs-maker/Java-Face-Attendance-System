package com.attendance.face.dto;

public class FaceRecognitionRequest {
    private String employeeId; // Optional if identifying purely from face descriptor match
    private String faceDescriptor; // JSON array string e.g. "[0.123, -0.045, ...]"
    private String capturedSnapshot; // Base64 snapshot image
    private String actionType; // "CHECK_IN" or "CHECK_OUT"

    public FaceRecognitionRequest() {}

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getFaceDescriptor() { return faceDescriptor; }
    public void setFaceDescriptor(String faceDescriptor) { this.faceDescriptor = faceDescriptor; }

    public String getCapturedSnapshot() { return capturedSnapshot; }
    public void setCapturedSnapshot(String capturedSnapshot) { this.capturedSnapshot = capturedSnapshot; }

    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
}
