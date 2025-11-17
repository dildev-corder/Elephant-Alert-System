package com.elephant.alerts;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ElephantAlertDAO {

    public List<ElephantAlert> getAlertsForElephant(String elephantId) {
        List<ElephantAlert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM elephant_alerts WHERE elephant_id = ? ORDER BY alert_time DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, elephantId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Timestamp ts = rs.getTimestamp("alert_time");
                    LocalDateTime ldt = ts.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    String village = rs.getString("village");
                    String risk = rs.getString("risk_level");
                    double dist = rs.getDouble("distance_km");
                    double lat = rs.getDouble("latitude");
                    double lon = rs.getDouble("longitude");
                    ElephantAlert alert = new ElephantAlert(id, elephantId, ldt, village, risk, dist, lat, lon);
                    alerts.add(alert);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return alerts;
    }
}
