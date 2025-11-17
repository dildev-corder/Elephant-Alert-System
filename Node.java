package com.elephant.alerts;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String id;
    private double latitude;
    private double longitude;
    private List<Edge> edges = new ArrayList<>();

    public Node(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return id;
    }
}
