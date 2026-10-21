package connectivity;

import graphs.Edge;
import graphs.Graph;

import java.util.ArrayList;
import java.util.Collections;

public class Kosaraju {

    public static class Result {
        public ArrayList<ArrayList<Integer>> components = new ArrayList<>();
        public long edgeChecks;
        public long reversedEdges;
    }

    public static Result findSCC(Graph graph) {
        if (!graph.directed) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        Result result = new Result();

        int n = graph.vertices;
        boolean[] visited = new boolean[n];
        ArrayList<Integer> order = new ArrayList<>();

        // Step 1: DFS on original graph
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i, graph, visited, order, result);
            }
        }

        // Step 2: Reverse all edges
        Graph reversed = new Graph(n, true);

        for (Edge edge : graph.edges) {
            reversed.addEdge(edge.to, edge.from, edge.weight);
            result.reversedEdges++;
        }

        // Step 3: DFS on reversed graph
        Collections.reverse(order);
        visited = new boolean[n];

        for (int vertex : order) {
            if (!visited[vertex]) {
                ArrayList<Integer> component = new ArrayList<>();

                dfs2(vertex, reversed, visited, component, result);

                result.components.add(component);
            }
        }

        return result;
    }

    private static void dfs1(
            int vertex,
            Graph graph,
            boolean[] visited,
            ArrayList<Integer> order,
            Result result) {

        visited[vertex] = true;

        for (Edge edge : graph.adj.get(vertex)) {
            result.edgeChecks++;

            if (!visited[edge.to]) {
                dfs1(edge.to, graph, visited, order, result);
            }
        }

        order.add(vertex);
    }

    private static void dfs2(
            int vertex,
            Graph graph,
            boolean[] visited,
            ArrayList<Integer> component,
            Result result) {

        visited[vertex] = true;
        component.add(vertex);

        for (Edge edge : graph.adj.get(vertex)) {
            result.edgeChecks++;

            if (!visited[edge.to]) {
                dfs2(edge.to, graph, visited, component, result);
            }
        }
    }
}