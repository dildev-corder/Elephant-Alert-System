package com.elephant.alerts;

import java.util.List;

public class PathResult {
    private final List<GraphNode> path;
    private final double totalWeight;

    public PathResult(List<GraphNode> path, double totalWeight) {
        this.path = path;
        this.totalWeight = totalWeight;
    }

    public List<GraphNode> getPath() { return path; }
    public double getTotalWeight() { return totalWeight; }
}
