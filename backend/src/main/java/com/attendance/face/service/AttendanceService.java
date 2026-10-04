package com.attendance.face.service;

import com.attendance.face.dto.AttendanceResponse;
import com.attendance.face.dto.FaceRecognitionRequest;
import com.attendance.face.entity.AttendanceRecord;
import com.attendance.face.entity.User;
import com.attendance.face.repository.AttendanceRepository;
import com.attendance.face.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    // Euclidean distance threshold for matching 128-dimensional face descriptors
    private static final double MATCH_THRESHOLD = 0.55;

    public AttendanceService(UserRepository userRepository, AttendanceRepository attendanceRepository) {
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmployeeId(user.getEmployeeId())) {
            throw new RuntimeException("Employee ID already registered: " + user.getEmployeeId());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public List<AttendanceRecord> getAllRecords() {
        return attendanceRepository.findAllByOrderByDateDescCheckInTimeDesc();
    }

    public List<AttendanceRecord> getTodayRecords() {
        return attendanceRepository.findByDate(LocalDate.now());
    }

    public AttendanceResponse processFaceAttendance(FaceRecognitionRequest request) {
        // --- 1. LIVENESS & ANTI-SPOOFING ENFORCEMENT ---
        if (request.getLivenessVerified() == null || !request.getLivenessVerified()) {
            return new AttendanceResponse(false, "Anti-Spoof Alert: Live human eye blink not detected! Showing a photo or screen is prohibited.");
        }

        if (request.getFaceDescriptor() == null || request.getFaceDescriptor().trim().isEmpty()) {
            return new AttendanceResponse(false, "Face descriptor data not provided.");
        }

        double[] inputVector;
        try {
            inputVector = parseVector(request.getFaceDescriptor());
        } catch (Exception e) {
            return new AttendanceResponse(false, "Malformed face vector data: " + e.getMessage());
        }

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new AttendanceResponse(false, "No registered employees found. Please register staff faces first.");
        }

        User bestMatch = null;
        double minDistance = Double.MAX_VALUE;

        for (User user : users) {
            if (user.getFaceDescriptor() != null && !user.getFaceDescriptor().trim().isEmpty()) {
                try {
                    double[] storedVector = parseVector(user.getFaceDescriptor());
                    double distance = calculateEuclideanDistance(inputVector, storedVector);
                    if (distance < minDistance) {
                        minDistance = distance;
                        bestMatch = user;
                    }
                } catch (Exception ignored) {}
            }
        }

        if (bestMatch == null || minDistance > MATCH_THRESHOLD) {
            return new AttendanceResponse(false, "Face not recognized. Closest distance: " + 
                    String.format("%.3f", minDistance) + " (Threshold is " + MATCH_THRESHOLD + ")");
        }

        // Record Attendance
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Optional<AttendanceRecord> existingOpt = attendanceRepository.findByUserAndDate(bestMatch, today);

        AttendanceRecord record;
        String action = request.getActionType() != null ? request.getActionType().toUpperCase() : "CHECK_IN";
        int blinks = request.getBlinkCount() != null ? request.getBlinkCount() : 1;

        if ("CHECK_OUT".equals(action)) {
            if (existingOpt.isPresent()) {
                record = existingOpt.get();
                record.setCheckOutTime(now);
                record.setLivenessVerified(true);
                attendanceRepository.save(record);
                return new AttendanceResponse(true, "Check-Out recorded successfully for " + bestMatch.getFullName(), bestMatch, record, minDistance);
            } else {
                record = new AttendanceRecord(bestMatch, today, null, "CHECK_OUT_ONLY", 1.0 - minDistance, request.getCapturedSnapshot(), true, blinks);
                record.setCheckOutTime(now);
                attendanceRepository.save(record);
                return new AttendanceResponse(true, "Check-Out recorded for " + bestMatch.getFullName(), bestMatch, record, minDistance);
            }
        } else {
            // Default CHECK_IN
            if (existingOpt.isPresent()) {
                record = existingOpt.get();
                return new AttendanceResponse(true, "Already checked in today at " + record.getCheckInTime() + " for " + bestMatch.getFullName(), bestMatch, record, minDistance);
            }

            // Normal office cutoff at 09:30 AM
            String status = now.isAfter(LocalTime.of(9, 30)) ? "LATE" : "PRESENT";
            record = new AttendanceRecord(bestMatch, today, now, status, 1.0 - minDistance, request.getCapturedSnapshot(), true, blinks);
            attendanceRepository.save(record);

            return new AttendanceResponse(true, "Live attendance marked successfully! Status: " + status + " (" + bestMatch.getFullName() + ")", bestMatch, record, minDistance);
        }
    }

    private double[] parseVector(String json) {
        String clean = json.replace("[", "").replace("]", "").replace("\"", "").trim();
        if (clean.isEmpty()) return new double[0];
        String[] parts = clean.split(",");
        double[] vector = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            vector[i] = Double.parseDouble(parts[i].trim());
        }
        return vector;
    }

    private double calculateEuclideanDistance(double[] v1, double[] v2) {
        if (v1.length != v2.length) return Double.MAX_VALUE;
        double sum = 0.0;
        for (int i = 0; i < v1.length; i++) {
            double diff = v1[i] - v2[i];
            sum += diff * diff;
        }
        return Math.sqrt(sum);
    }
}
