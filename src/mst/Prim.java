package mst;

import graphs.Edge;
import graphs.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Prim {

    public static class Result {
        public ArrayList<Edge> mst = new ArrayList<>();
        public long totalWeight;
        public boolean connected;

        public long edgeChecks;
        public long queueAdds;
        public long queuePolls;
    }

    public static Result findMST(Graph graph) {
        if (graph.directed) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        Result result = new Result();

        if (graph.vertices == 0) {
            return result;
        }

        boolean[] visited = new boolean[graph.vertices];

        PriorityQueue<Edge> pq = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.weight)
        );

        // Start from vertex 0
        visited[0] = true;

        for (Edge edge : graph.adj.get(0)) {
            result.edgeChecks++;

            if (!visited[edge.to]) {
                pq.add(edge);
                result.queueAdds++;
            }
        }

        // Build MST
        while (!pq.isEmpty() && result.mst.size() < graph.vertices - 1) {

            Edge edge = pq.poll();
            result.queuePolls++;

            if (visited[edge.to]) {
                continue;
            }

            visited[edge.to] = true;
            result.mst.add(edge);
            result.totalWeight += edge.weight;

            // Add outgoing edges of the new vertex
            for (Edge next : graph.adj.get(edge.to)) {
                result.edgeChecks++;

                if (!visited[next.to]) {
                    pq.add(next);
                    result.queueAdds++;
                }
            }
        }

        result.connected = result.mst.size() == graph.vertices - 1;

        return result;
    }
}