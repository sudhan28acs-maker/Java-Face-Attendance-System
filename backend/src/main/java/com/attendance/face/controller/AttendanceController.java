package com.attendance.face.controller;

import com.attendance.face.dto.AttendanceResponse;
import com.attendance.face.dto.FaceRecognitionRequest;
import com.attendance.face.entity.AttendanceRecord;
import com.attendance.face.entity.User;
import com.attendance.face.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows React UI to communicate seamlessly
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(attendanceService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User saved = attendanceService.registerUser(user);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/attendance/recognize")
    public ResponseEntity<AttendanceResponse> markFaceAttendance(@RequestBody FaceRecognitionRequest request) {
        AttendanceResponse response = attendanceService.processFaceAttendance(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(400).body(response);
        }
    }

    @GetMapping("/attendance/today")
    public ResponseEntity<List<AttendanceRecord>> getTodayAttendance() {
        return ResponseEntity.ok(attendanceService.getTodayRecords());
    }

    @GetMapping("/attendance/history")
    public ResponseEntity<List<AttendanceRecord>> getAttendanceHistory() {
        return ResponseEntity.ok(attendanceService.getAllRecords());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Face Attendance Backend is Running smoothly!");
    }
}
