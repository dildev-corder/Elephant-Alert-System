CREATE DATABASE IF NOT EXISTS elephant_db;
USE elephant_db;

CREATE TABLE IF NOT EXISTS elephant_alerts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    elephant_id VARCHAR(20) NOT NULL,
    alert_time DATETIME NOT NULL,
    village VARCHAR(100) NOT NULL,
    risk_level VARCHAR(20) NOT NULL,
    distance_km DOUBLE NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

INSERT INTO elephant_alerts (elephant_id, alert_time, village, risk_level, distance_km, latitude, longitude) VALUES
('E-001', '2025-11-15 18:30:00', 'Galoya Village', 'High', 1.2, 7.743, 81.609),
('E-001', '2025-11-15 17:10:00', 'Maduru Oya Border', 'Medium', 3.8, 7.838, 81.299),
('E-001', '2025-11-15 16:45:00', 'Mahiyanganaya North', 'High', 2.4, 7.333, 81.000),
('E-001', '2025-11-15 15:20:00', 'Polonnaruwa Sector 3', 'Low', 6.5, 7.939, 81.000);
