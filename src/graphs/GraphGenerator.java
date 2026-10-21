package graphs;

import java.util.HashSet;
import java.util.Random;

public class GraphGenerator {

    public static Graph connectedUndirected(int vertices, int edgeCount, long seed) {
        Graph graph = new Graph(vertices, false);
        Random random = new Random(seed);
        HashSet<Long> used = new HashSet<>();

        // First connect all vertices in a chain
        for (int i = 1; i < vertices; i++) {
            graph.addEdge(i - 1, i, random.nextInt(100) + 1);
            used.add((long) (i - 1) * vertices + i);
        }

        // Add random edges
        while (graph.edges.size() < edgeCount) {
            int a = random.nextInt(vertices);
            int b = random.nextInt(vertices);

            if (a == b) continue;

            int from = Math.min(a, b);
            int to = Math.max(a, b);
            long key = (long) from * vertices + to;

            if (used.add(key)) {
                graph.addEdge(from, to, random.nextInt(100) + 1);
            }
        }

        return graph;
    }

    public static Graph dag(int vertices, int edgeCount, long seed) {
        Graph graph = new Graph(vertices, true);
        Random random = new Random(seed);
        HashSet<Long> used = new HashSet<>();

        // All edges go from smaller to larger vertices
        for (int i = 1; i < vertices; i++) {
            graph.addEdge(i - 1, i, random.nextInt(100) + 1);
            used.add((long) (i - 1) * vertices + i);
        }

        while (graph.edges.size() < edgeCount) {
            int a = random.nextInt(vertices);
            int b = random.nextInt(vertices);

            if (a == b) continue;

            int from = Math.min(a, b);
            int to = Math.max(a, b);
            long key = (long) from * vertices + to;

            if (used.add(key)) {
                graph.addEdge(from, to, random.nextInt(100) + 1);
            }
        }

        return graph;
    }

    public static Graph directedWithCycles(int vertices, int edgeCount, long seed) {
        Graph graph = new Graph(vertices, true);
        Random random = new Random(seed);
        HashSet<Long> used = new HashSet<>();

        // Create a cycle: 0 -> 1 -> 2 -> 0
        for (int i = 0; i < 3; i++) {
            int from = i;
            int to = (i + 1) % 3;

            graph.addEdge(from, to, random.nextInt(100) + 1);
            used.add((long) from * vertices + to);
        }

        while (graph.edges.size() < edgeCount) {
            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            if (from == to) continue;

            long key = (long) from * vertices + to;

            if (used.add(key)) {
                graph.addEdge(from, to, random.nextInt(100) + 1);
            }
        }

        return graph;
    }
}