package mst;

import graphs.Edge;
import graphs.Graph;
import dsu.UnionFind;

import java.util.ArrayList;
import java.util.Comparator;

public class Kruskal {

    public static void findMST(Graph graph) {

        if (graph.directed) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        // Copy and sort edges by weight
        ArrayList<Edge> edges = new ArrayList<>(graph.edges);
        edges.sort(Comparator.comparingInt(e -> e.weight));

        // Create DSU
        UnionFind dsu = new UnionFind(graph.vertices);

        int totalWeight = 0;
        int edgeCount = 0;

        // Find MST
        for (Edge edge : edges) {

            if (dsu.union(edge.from, edge.to)) {
                totalWeight += edge.weight;
                edgeCount++;

                System.out.println(
                        edge.from + " - " + edge.to +
                                " : " + edge.weight
                );
            }

            if (edgeCount == graph.vertices - 1) {
                break;
            }
        }

        // Check connectivity
        if (edgeCount == graph.vertices - 1) {
            System.out.println("MST weight: " + totalWeight);
        } else {
            System.out.println("Graph is disconnected. MST does not exist.");
        }
    }
}