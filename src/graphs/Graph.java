package graphs;

import java.util.ArrayList;

public class Graph {
    public int vertices;
    public boolean directed;

    public ArrayList<Edge> edges = new ArrayList<>();
    public ArrayList<ArrayList<Edge>> adj = new ArrayList<>();

    public Graph(int vertices, boolean directed) {
        this.vertices = vertices;
        this.directed = directed;

        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        if (from < 0 || from >= vertices || to < 0 || to >= vertices) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        Edge edge = new Edge(from, to, weight);

        edges.add(edge);
        adj.get(from).add(edge);

        if (!directed) {
            adj.get(to).add(new Edge(to, from, weight));
        }
    }
}