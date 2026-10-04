#!/usr/bin/env bash
echo "Starting Spring Boot Face Attendance Backend..."
cd "$(dirname "$0")/backend"
mvn spring-boot:run
