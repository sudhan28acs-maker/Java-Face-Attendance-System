# 🚀 Face Attendance System (Java Spring Boot + React + MySQL)

An end-to-end AI Face Recognition Attendance application built using:
- **Backend**: Java 21, Spring Boot 3, Spring Data JPA, Hibernate, REST APIs.
- **Frontend**: React 18, HTML5, CSS3, Lucide Icons, WebRTC Camera stream.
- **Biometric Face Recognition**: Real-time 128-dimensional facial embedding vector extraction & Euclidean distance calculation matching algorithm.
- **Database**: MySQL (compatible with XAMPP or native MySQL) / H2 in-memory fallback.

---

## 📁 Project Architecture

```
Java Face Attendance System/
├── backend/
│   ├── src/main/java/com/attendance/face/
│   │   ├── FaceAttendanceApplication.java
│   │   ├── controller/AttendanceController.java
│   │   ├── entity/User.java
│   │   ├── entity/AttendanceRecord.java
│   │   ├── repository/UserRepository.java
│   │   ├── repository/AttendanceRepository.java
│   │   ├── service/AttendanceService.java
│   │   └── dto/
│   │       ├── FaceRecognitionRequest.java
│   │       └── AttendanceResponse.java
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── application-mysql.properties
│   └── pom.xml
├── frontend/
│   ├── public/index.html
│   ├── src/
│   │   ├── App.js
│   │   ├── App.css
│   │   └── index.js
│   └── package.json
├── database_schema.sql
├── run_backend.sh
└── run_frontend.sh
```

---

## 🛠️ Technology Stack Breakdown

1. **Java & Spring Boot (Backend)**:
   - Manages employee profiles and biometric descriptors.
   - Computes Euclidean distance matrix across face vectors to recognize employees without storing raw proprietary images on disk.
   - Provides REST endpoints for live check-in, check-out, staff listings, and logs.

2. **MySQL / Database**:
   - Stores employee records, 128D descriptor JSON, and timestamps.
   - Included `database_schema.sql` can be imported directly into phpMyAdmin or MySQL CLI.

3. **React 18 & HTML5/CSS3 (Frontend)**:
   - Modern dark UI with real-time video viewfinder and animated reticle overlay.
   - Live Scanner Tab: Instant Face ID Check-In and Check-Out.
   - Registration Tab: Captures facial biometrics directly from webcam, extracts template vector, and associates employee profile.
   - Logs & Staff Directory: Visual view of daily attendances, confidence scores, and enrolled employees.

---

## 🚀 How to Run the Application

### 1. Database (Optional - Pre-configured fallback active)
If using XAMPP or local MySQL:
- Start MySQL in XAMPP (`sudo /opt/lampp/lampp startmysql`).
- Execute SQL queries in `database_schema.sql` via phpMyAdmin or MySQL console.
- Run Spring Boot with MySQL profile:
  ```bash
  mvn spring-boot:run -Dspring-boot.run.profiles=mysql
  ```
*(Note: By default, Spring Boot runs with in-memory H2 database out of the box so you can test immediately even without MySQL started)*

### 2. Run Backend
In the project root, open a terminal:
```bash
./run_backend.sh
```
Or:
```bash
cd backend
mvn spring-boot:run
```
Backend will start on: **`http://localhost:8080`**

### 3. Run Frontend
In another terminal:
```bash
./run_frontend.sh
```
Or:
```bash
cd frontend
npm start
```
Frontend will automatically open at: **`http://localhost:3000`**

---

## 🎯 Features
- 🔍 **Real-time Face Detection & Recognition**: Multi-stage landmark mapping and 128D neural vector extraction.
- 🕒 **Automatic Check-In / Check-Out**: Detects on-time vs late punch-in (cutoff 9:30 AM).
- 🛡️ **Anti-duplicate Prevention**: Guards against multiple check-ins on the same day.
- 👥 **Staff Management**: Instant biometric onboarding and profile directory.
