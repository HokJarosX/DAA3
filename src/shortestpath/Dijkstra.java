package shortestpath;

import graphs.Edge;
import graphs.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {

    private static class Node {
        int vertex;
        long distance;

        Node(int vertex, long distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    public static class Result {
        public long[] distances;
        public int[] parent;

        public long edgeChecks;
        public long queueAdds;
        public long queuePolls;

        Result(int n) {
            distances = new long[n];
            parent = new int[n];

            Arrays.fill(distances, Long.MAX_VALUE);
            Arrays.fill(parent, -1);
        }

        public ArrayList<Integer> getPathTo(int target) {
            ArrayList<Integer> path = new ArrayList<>();

            if (distances[target] == Long.MAX_VALUE) {
                return path;
            }

            int current = target;

            while (current != -1) {
                path.add(current);
                current = parent[current];
            }

            Collections.reverse(path);

            return path;
        }
    }

    public static Result shortestPaths(Graph graph, int source) {
        if (source < 0 || source >= graph.vertices) {
            throw new IllegalArgumentException("Invalid source vertex");
        }

        // Dijkstra requires non-negative weights
        for (Edge edge : graph.edges) {
            if (edge.weight < 0) {
                throw new IllegalArgumentException(
                        "Negative edge weights are not allowed"
                );
            }
        }

        Result result = new Result(graph.vertices);

        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingLong(n -> n.distance)
        );

        result.distances[source] = 0;
        pq.add(new Node(source, 0));
        result.queueAdds++;

        while (!pq.isEmpty()) {

            Node current = pq.poll();
            result.queuePolls++;

            int vertex = current.vertex;

            // Skip outdated entries
            if (current.distance != result.distances[vertex]) {
                continue;
            }

            for (Edge edge : graph.adj.get(vertex)) {
                result.edgeChecks++;

                int next = edge.to;
                long newDistance = result.distances[vertex] + edge.weight;

                if (newDistance < result.distances[next]) {
                    result.distances[next] = newDistance;
                    result.parent[next] = vertex;

                    pq.add(new Node(next, newDistance));
                    result.queueAdds++;
                }
            }
        }

        return result;
    }
}