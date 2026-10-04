# ===================================================
# Face Recognition Attendance System Database Script
# Target: MySQL / MariaDB (e.g. XAMPP or local MySQL)
# ===================================================

CREATE DATABASE IF NOT EXISTS attendance_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE attendance_db;

-- 1. Table for Registered Employees / Users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(100),
    role VARCHAR(100),
    face_descriptor LONGTEXT NOT NULL COMMENT '128-dimensional embedding vector in JSON format',
    photo_data LONGTEXT COMMENT 'Base64 image snapshot',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 2. Table for Daily Attendance Logs
CREATE TABLE IF NOT EXISTS attendance_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    date DATE NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    status VARCHAR(20) DEFAULT 'PRESENT' COMMENT 'PRESENT, LATE, HALF_DAY, CHECK_OUT_ONLY',
    confidence_score DOUBLE DEFAULT 0.95,
    captured_snapshot LONGTEXT COMMENT 'Base64 image taken at scan point',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_daily_attendance (user_id, date)
) ENGINE=InnoDB;

-- Sample query to check records
-- SELECT u.employee_id, u.full_name, u.department, a.date, a.check_in_time, a.check_out_time, a.status 
-- FROM attendance_records a 
-- JOIN users u ON a.user_id = u.id 
-- ORDER BY a.date DESC, a.check_in_time DESC;
