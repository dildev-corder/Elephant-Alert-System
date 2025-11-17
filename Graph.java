package com.elephant.alerts;

import java.util.*;

public class Graph {

    private final Map<String, GraphNode> nodes = new HashMap<>();
    private final Map<GraphNode, List<GraphEdge>> adj = new HashMap<>();

    public GraphNode addNode(String id, String name, double lat, double lng) {
        GraphNode node = new GraphNode(id, name, lat, lng);
        nodes.put(id, node);
        adj.put(node, new ArrayList<>());
        return node;
    }

    public void addUndirectedEdge(GraphNode a, GraphNode b, double weight) {
        adj.get(a).add(new GraphEdge(a, b, weight));
        adj.get(b).add(new GraphEdge(b, a, weight));
    }

    public List<GraphEdge> getEdges(GraphNode node) {
        return adj.getOrDefault(node, Collections.emptyList());
    }

    public Collection<GraphNode> getNodes() {
        return nodes.values();
    }
}
