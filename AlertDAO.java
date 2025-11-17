package com.elephant.alerts;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    public List<Alert> findAllLatest(int limit) {
        List<Alert> list = new ArrayList<>();

        String sql = "SELECT id, time, elephant_id, zone, risk_level, status " +
                "FROM alerts ORDER BY time DESC LIMIT ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Timestamp ts = rs.getTimestamp("time");
                    LocalDateTime time = ts.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    String elephantId = rs.getString("elephant_id");
                    String zone = rs.getString("zone");
                    String risk = rs.getString("risk_level");
                    String status = rs.getString("status");

                    list.add(new Alert(id, time, elephantId, zone, risk, status));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
