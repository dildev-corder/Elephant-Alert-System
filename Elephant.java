package com.elephant.alerts;

public class Elephant {
    private String id;
    private String name;
    private String status;
    private String location;
    private double latitude;
    private double longitude;

    public Elephant(String id, String name, String status, String location,
                    double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getLocation() { return location; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
