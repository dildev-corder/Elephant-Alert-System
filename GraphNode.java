package com.elephant.alerts;

public class GraphNode {
    private final String id;
    private final String name;
    private final double lat;
    private final double lng;

    public GraphNode(String id, String name, double lat, double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }

    @Override
    public String toString() {
        return name;
    }
}
