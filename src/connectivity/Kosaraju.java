package connectivity;

import graphs.Graph;
import graphs.Edge;

import java.util.ArrayList;
import java.util.Collections;

public class Kosaraju {

    public static ArrayList<ArrayList<Integer>> findSCC(Graph graph) {

        if (!graph.directed) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        int n = graph.vertices;
        boolean[] visited = new boolean[n];
        ArrayList<Integer> order = new ArrayList<>();

        // Step 1: DFS on original graph
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i, graph, visited, order);
            }
        }

        // Step 2: Reverse all edges
        Graph reversed = new Graph(n, true);

        for (Edge edge : graph.edges) {
            reversed.addEdge(edge.to, edge.from, edge.weight);
        }

        // Step 3: DFS on reversed graph
        Collections.reverse(order);
        visited = new boolean[n];

        ArrayList<ArrayList<Integer>> components = new ArrayList<>();

        for (int vertex : order) {
            if (!visited[vertex]) {
                ArrayList<Integer> component = new ArrayList<>();

                dfs2(vertex, reversed, visited, component);

                components.add(component);
            }
        }

        return components;
    }

    private static void dfs1(
            int vertex,
            Graph graph,
            boolean[] visited,
            ArrayList<Integer> order) {

        visited[vertex] = true;

        for (Edge edge : graph.adj.get(vertex)) {
            if (!visited[edge.to]) {
                dfs1(edge.to, graph, visited, order);
            }
        }

        order.add(vertex);
    }

    private static void dfs2(
            int vertex,
            Graph graph,
            boolean[] visited,
            ArrayList<Integer> component) {

        visited[vertex] = true;
        component.add(vertex);

        for (Edge edge : graph.adj.get(vertex)) {
            if (!visited[edge.to]) {
                dfs2(edge.to, graph, visited, component);
            }
        }
    }
}