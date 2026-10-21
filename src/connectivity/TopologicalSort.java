package connectivity;

import graphs.Graph;
import graphs.Edge;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class TopologicalSort {

    public static class Result {
        public ArrayList<Integer> order = new ArrayList<>();
        public long edgeChecks;
    }

    public static Result sort(Graph graph) {
        if (!graph.directed) {
            throw new IllegalArgumentException("Graph must be directed");
        }

        Result result = new Result();

        int[] inDegree = new int[graph.vertices];

        // Count incoming edges
        for (Edge edge : graph.edges) {
            result.edgeChecks++;
            inDegree[edge.to]++;
        }

        // Find vertices with no incoming edges
        Queue<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < graph.vertices; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // Topological ordering
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.order.add(current);

            for (Edge edge : graph.adj.get(current)) {
                result.edgeChecks++;
                inDegree[edge.to]--;

                if (inDegree[edge.to] == 0) {
                    queue.add(edge.to);
                }
            }
        }

        // Detect cycle
        if (result.order.size() != graph.vertices) {
            result.order = null;
        }

        return result;
    }
}