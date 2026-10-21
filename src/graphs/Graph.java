package graphs;

import java.util.ArrayList;

public class Graph {
    int vertices;
    boolean directed;

    ArrayList<Edge> edges;
    ArrayList<ArrayList<Edge>> adj;

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;

        edges = new ArrayList<>();
        adj = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);

        edges.add(edge);
        adj.get(from).add(edge);

        if (!directed) {
            adj.get(to).add(new Edge(to, from, weight));
        }
    }
}