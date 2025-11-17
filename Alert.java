package com.elephant.alerts;

import java.time.LocalDateTime;

public class Alert {
    private int id;
    private LocalDateTime time;
    private String elephantId;
    private String zone;
    private String riskLevel;
    private String status;

    public Alert(int id, LocalDateTime time, String elephantId,
                 String zone, String riskLevel, String status) {
        this.id = id;
        this.time = time;
        this.elephantId = elephantId;
        this.zone = zone;
        this.riskLevel = riskLevel;
        this.status = status;
    }

    public int getId() { return id; }
    public LocalDateTime getTime() { return time; }
    public String getElephantId() { return elephantId; }
    public String getZone() { return zone; }
    public String getRiskLevel() { return riskLevel; }
    public String getStatus() { return status; }
}
