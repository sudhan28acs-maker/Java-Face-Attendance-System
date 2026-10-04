package com.attendance.face.repository;

import com.attendance.face.entity.AttendanceRecord;
import com.attendance.face.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    List<AttendanceRecord> findByDate(LocalDate date);
    List<AttendanceRecord> findByUser(User user);
    Optional<AttendanceRecord> findByUserAndDate(User user, LocalDate date);
    List<AttendanceRecord> findAllByOrderByDateDescCheckInTimeDesc();
}
