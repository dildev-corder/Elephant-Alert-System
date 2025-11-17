package com.elephant.alerts;

import java.time.LocalDateTime;

public class ElephantAlert {
    private int id;
    private String elephantId;
    private LocalDateTime alertTime;
    private String village;
    private String riskLevel;
    private double distanceKm;
    private double latitude;
    private double longitude;

    public ElephantAlert(int id, String elephantId, LocalDateTime alertTime,
                         String village, String riskLevel, double distanceKm,
                         double latitude, double longitude) {
        this.id = id;
        this.elephantId = elephantId;
        this.alertTime = alertTime;
        this.village = village;
        this.riskLevel = riskLevel;
        this.distanceKm = distanceKm;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() { return id; }
    public String getElephantId() { return elephantId; }
    public LocalDateTime getAlertTime() { return alertTime; }
    public String getVillage() { return village; }
    public String getRiskLevel() { return riskLevel; }
    public double getDistanceKm() { return distanceKm; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
