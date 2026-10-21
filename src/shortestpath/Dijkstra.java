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

        Result(long[] distances, int[] parent) {
            this.distances = distances;
            this.parent = parent;
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

        int n = graph.vertices;

        if (source < 0 || source >= n) {
            throw new IllegalArgumentException("Invalid source vertex");
        }

        // Dijkstra does not support negative weights
        for (Edge edge : graph.edges) {
            if (edge.weight < 0) {
                throw new IllegalArgumentException(
                        "Negative edge weights are not allowed"
                );
            }
        }

        long[] distances = new long[n];
        int[] parent = new int[n];

        Arrays.fill(distances, Long.MAX_VALUE);
        Arrays.fill(parent, -1);

        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingLong(node -> node.distance)
        );

        distances[source] = 0;
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {

            Node current = pq.poll();
            int vertex = current.vertex;

            // Skip outdated queue entries
            if (current.distance != distances[vertex]) {
                continue;
            }

            for (Edge edge : graph.adj.get(vertex)) {

                int next = edge.to;
                long newDistance = distances[vertex] + edge.weight;

                if (newDistance < distances[next]) {
                    distances[next] = newDistance;
                    parent[next] = vertex;

                    pq.add(new Node(next, newDistance));
                }
            }
        }

        return new Result(distances, parent);
    }
}