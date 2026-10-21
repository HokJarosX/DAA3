package mst;

import graphs.Edge;
import graphs.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Prim {

    public static void findMST(Graph graph) {

        if (graph.directed) {
            throw new IllegalArgumentException("Graph must be undirected");
        }

        if (graph.vertices == 0) {
            System.out.println("Graph is empty");
            return;
        }

        boolean[] visited = new boolean[graph.vertices];

        PriorityQueue<Edge> pq = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.weight)
        );

        ArrayList<Edge> mst = new ArrayList<>();

        int totalWeight = 0;
        int edgeCount = 0;

        // Start from vertex 0
        visited[0] = true;
        pq.addAll(graph.adj.get(0));

        while (!pq.isEmpty() && edgeCount < graph.vertices - 1) {

            Edge edge = pq.poll();

            if (visited[edge.to]) {
                continue;
            }

            visited[edge.to] = true;
            mst.add(edge);

            totalWeight += edge.weight;
            edgeCount++;

            // Add outgoing edges of the new vertex
            for (Edge next : graph.adj.get(edge.to)) {
                if (!visited[next.to]) {
                    pq.add(next);
                }
            }
        }

        if (edgeCount == graph.vertices - 1) {
            for (Edge edge : mst) {
                System.out.println(
                        edge.from + " - " + edge.to +
                                " : " + edge.weight
                );
            }

            System.out.println("MST weight: " + totalWeight);
        } else {
            System.out.println("Graph is disconnected. MST does not exist.");
        }
    }
}