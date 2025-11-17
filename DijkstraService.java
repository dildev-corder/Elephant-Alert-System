package com.elephant.alerts;

import java.util.*;

public class DijkstraService {

    public PathResult shortestPath(Graph graph, GraphNode source, GraphNode target) {
        Map<GraphNode, Double> dist = new HashMap<>();
        Map<GraphNode, GraphNode> prev = new HashMap<>();

        for (GraphNode n : graph.getNodes()) {
            dist.put(n, Double.POSITIVE_INFINITY);
        }
        dist.put(source, 0.0);

        PriorityQueue<GraphNode> pq = new PriorityQueue<>(
                Comparator.comparingDouble(dist::get)
        );
        pq.add(source);

        while (!pq.isEmpty()) {
            GraphNode u = pq.poll();
            if (u == target) break;

            for (GraphEdge e : graph.getEdges(u)) {
                GraphNode v = e.getTo();
                double alt = dist.get(u) + e.getWeight();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        if (!prev.containsKey(target) && source != target) {
            return new PathResult(Collections.emptyList(), Double.POSITIVE_INFINITY);
        }

        // reconstruct path
        List<GraphNode> path = new ArrayList<>();
        GraphNode step = target;
        path.add(step);
        while (prev.containsKey(step)) {
            step = prev.get(step);
            path.add(step);
        }
        Collections.reverse(path);

        return new PathResult(path, dist.get(target));
    }
}
