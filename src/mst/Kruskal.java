package mst;

import graphs.Edge;
import graphs.Graph;
import dsu.UnionFind;

import java.util.ArrayList;

public class Kruskal {

    public static class Result {
        public ArrayList<Edge> mst = new ArrayList<>();
        public long totalWeight;
        public boolean connected;

        public long edgeChecks;
        public long sortComparisons;
        public long findCalls;
        public long unionCalls;
    }

    public static Result findMST(Graph graph) {
        if (graph.directed) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        Result result = new Result();

        // Copy and sort edges
        ArrayList<Edge> edges = new ArrayList<>(graph.edges);

        edges.sort((a, b) -> {
            result.sortComparisons++;
            return Integer.compare(a.weight, b.weight);
        });

        UnionFind dsu = new UnionFind(graph.vertices);

        // Build MST
        for (Edge edge : edges) {
            result.edgeChecks++;

            if (dsu.union(edge.from, edge.to)) {
                result.mst.add(edge);
                result.totalWeight += edge.weight;
            }

            if (result.mst.size() == graph.vertices - 1) {
                break;
            }
        }

        // Check connectivity
        result.connected = graph.vertices > 0
                && result.mst.size() == graph.vertices - 1;

        // Save DSU metrics
        result.findCalls = dsu.findCalls;
        result.unionCalls = dsu.unionCalls;

        return result;
    }
}