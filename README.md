# 🚀 AI Face Attendance System (Spring Boot + React + MySQL)

An enterprise-ready biometric face attendance web application featuring **Anti-Spoofing (Liveness / Eye-Blink Verification)**, built with:
- **Backend**: Java 21, Spring Boot 3.2.5, Spring Data JPA, Hibernate, REST APIs.
- **Frontend**: React 18, HTML5 Canvas/WebRTC, Modern Dark Mode UI.
- **Database**: MySQL / MariaDB (via XAMPP & phpMyAdmin) with automatic schema migration.
- **Biometrics & Security**: Real-time 68-landmark facial tracking, 128-dimensional embedding vectors, Eye Aspect Ratio (EAR) blink detection, and Euclidean distance vector matching.

---

## 📁 Repository Structure

```
Java Face Attendance System/
├── backend/
│   ├── src/main/java/com/attendance/face/
│   │   ├── FaceAttendanceApplication.java       # Spring Boot main runner
│   │   ├── controller/AttendanceController.java # REST API endpoints (/api/attendance, /api/users)
│   │   ├── entity/User.java                     # Staff profile & 128D biometric vector
│   │   ├── entity/AttendanceRecord.java         # Daily attendance records & liveness audit
│   │   ├── repository/UserRepository.java       # Database repository for staff
│   │   ├── repository/AttendanceRepository.java # Database repository for attendance
│   │   ├── service/AttendanceService.java       # Biometric matching & anti-spoof checks
│   │   └── dto/                                 # Request / Response payloads
│   ├── src/main/resources/
│   │   ├── application.properties               # XAMPP MySQL configuration
│   │   └── application-mysql.properties
│   └── pom.xml
├── frontend/                                    # Standard React project files
├── web-ui/
│   └── index.html                               # Standalone React UI with Liveness HUD & Eye-Blink Scanner
├── database_schema.sql                          # MySQL schema for phpMyAdmin
├── run_backend.sh                               # Quick-start script for backend
├── run_frontend.sh                              # Quick-start script for frontend
└── README.md
```

---

## 🛡️ Anti-Spoofing & Liveness Capabilities

1. **Static Photo Detection**:
   - Uses **Eye Aspect Ratio (EAR)** calculated at 30 FPS across 68 facial landmarks:
     $$\text{EAR} = \frac{\|p_2 - p_6\| + \|p_3 - p_5\|}{2 \|p_1 - p_4\|}$$
   - When a person blinks, EAR dips below `0.22` and returns above `0.27`.
   - Flat paper photos or static phone pictures maintain a constant EAR value ($\Delta = 0$) and are blocked from checking in.
2. **Backend Enforcement**:
   - Attendance requests strictly validate `livenessVerified == true` and log the recorded blink counts to prevent API tampering.

---

## 🗄️ Database Setup (XAMPP MySQL / phpMyAdmin)

1. Make sure your XAMPP MySQL server is running.
2. Open phpMyAdmin at **`http://localhost/phpmyadmin`**.
3. The database is configured as **`attendance_db`**:
   - `users`: Stores registered staff and 128-dimensional biometric embeddings.
   - `attendance_records`: Stores date, time, check-in, check-out, confidence scores, and verified liveness data.

---

## 🚀 How to Execute the Project

### 1. Start the Java Backend
```bash
cd ~/Desktop/"Java Face Attendance System"
./run_backend.sh
```
*Backend runs on `http://localhost:8080` and connects directly to your XAMPP database.*

### 2. Start the Frontend Web UI
In a second terminal:
```bash
cd ~/Desktop/"Java Face Attendance System"
python3 -m http.server 3000 --directory web-ui
```
*Frontend interface is available at: `http://localhost:3000`.*

---

## 📌 Future Enhancements Roadmap (Replay Video Attack Prevention)
- [ ] **Dynamic Random Challenge-Response**: Randomized real-time challenge sequences (e.g. *Turn head left/right*, *Smile*, *Nod*) within 3-second windows to defeat recorded phone playback videos.
- [ ] **Screen Reflection Photometry**: Ambient color pulse reflection to detect phone screen glass versus human skin.
- [ ] **Moiré / High-Frequency Spectral Filter**: Frequency-domain analysis to spot digital display pixel grids.
