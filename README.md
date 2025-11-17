🐘 Elephant Movement Tracking & Safe Route Planning System
A Java-based web application designed to monitor elephant movements, predict potential human-wildlife conflicts, and generate safe evacuation routes using Dijkstra's Algorithm. The system provides real-time alerts, interactive maps, and analytics to assist in wildlife management and public safety.

✨ Features
🌍 Live Tracking Dashboard

Real-time elephant positions using Google Maps API

Interactive risk heatmaps and zone highlighting

🚨 Alerts Management

Real-time alerts panel with search, sort, and filter by risk level

Connected to a MySQL backend for live updates

🛡️ Safe Route Planning

Implements Dijkstra’s Algorithm to generate safest and shortest paths

Supports alternative evacuation routes and nearest safe zone identification

📊 Analytics & Reporting

Daily/monthly charts for alerts and zone-based statistics

Exportable reports in PDF format using Apache PDFBox

🐘 Elephant Profiles

Detailed elephant information, movement history, and last-known location

Downloadable PDF profiles for offline use

🗂️ System Architecture
Data Structures & Algorithms
Dijkstra’s Algorithm for shortest path calculation

Priority Queue for efficient node processing

ArrayList for storing and sorting alerts

Abstract Table Model for UI-data binding

Database Design (MySQL)
Elephants: id, name, status, zone, lat, lng

Alerts: alert_id, elephant_id, risk_level, timestamp, status

Zones: zone_name, risk_value

Routes: from_node, to_node, distance

Tech Stack
Backend: Java, Servlets, MySQL

Frontend: JSP, HTML/CSS, JavaScript, Google Maps API

Libraries: Apache PDFBox, Collections Framework

📁 Project Structure
text
src/
├── controller/       # Servlets and request handlers
├── model/           # Data models and DAOs
├── view/            # JSP pages and UI components
├── util/            # Helpers (e.g., PDF export, Dijkstra algo)
└── resources/       # SQL scripts, config files
🚀 Getting Started
Prerequisites
Java 8+

MySQL 5.7+

Apache Tomcat 9+

Installation
Clone the repository:

bash
git clone https://github.com/dildev-corder/
Elephant-Alert-System.git

Import the SQL script from resources/database.sql into MySQL.

Update src/resources/db.properties with your database credentials.

Deploy the WAR file to Tomcat and start the server.

📸 Screenshots

![WhatsApp Image 2025-11-16 at 22 42 17_7ec95cb7](https://github.com/user-attachments/assets/030ed152-1509-4254-9f14-9e64deb314c0)

📈 Future Enhancements
🛰️ IoT Collars for real-time GPS tracking

🤖 Machine Learning for predictive risk modeling

🚁 Drone Monitoring Integration

📩 SMS Alerts for high-risk zones

👨‍💻 Developed By
Sahan Devinda Pathirana
LinkedIn | Portfolio

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.
