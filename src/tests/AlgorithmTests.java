package tests;

import connectivity.Kosaraju;
import connectivity.TopologicalSort;
import graphs.Edge;
import graphs.Graph;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;

public class AlgorithmTests {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void run() {

        // Test 1: Connected graph, MST weight = 6
        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(0, 3, 5);

        Kruskal.Result k = Kruskal.findMST(graph);
        Prim.Result p = Prim.findMST(graph);

        check(k.connected && p.connected, "Connected MST test");
        check(k.totalWeight == 6 && p.totalWeight == 6, "MST weight");
        check(k.mst.size() == 3 && p.mst.size() == 3, "MST edge count");


        // Test 2: Disconnected graph
        Graph disconnected = new Graph(4, false);

        disconnected.addEdge(0, 1, 2);
        disconnected.addEdge(2, 3, 4);

        check(!Kruskal.findMST(disconnected).connected, "Disconnected Kruskal");
        check(!Prim.findMST(disconnected).connected, "Disconnected Prim");


        // Test 3: Unreachable vertices
        Dijkstra.Result unreachable = Dijkstra.shortestPaths(disconnected, 0);

        check(unreachable.distances[3] == Long.MAX_VALUE, "Unreachable distance");
        check(unreachable.getPathTo(3).isEmpty(), "Unreachable path");


        // Test 4: Multiple MSTs with equal edge weights
        Graph equalWeights = new Graph(4, false);

        equalWeights.addEdge(0, 1, 1);
        equalWeights.addEdge(1, 2, 1);
        equalWeights.addEdge(2, 3, 1);
        equalWeights.addEdge(3, 0, 1);

        check(Kruskal.findMST(equalWeights).totalWeight == 3, "Equal weights Kruskal");
        check(Prim.findMST(equalWeights).totalWeight == 3, "Equal weights Prim");


        // Test 5: Single vertex
        Graph single = new Graph(1, false);

        check(Kruskal.findMST(single).connected, "Single vertex Kruskal");
        check(Prim.findMST(single).connected, "Single vertex Prim");

        check(
                Dijkstra.shortestPaths(single, 0).getPathTo(0).equals(Arrays.asList(0)),
                "Single path"
        );


        // Test 6: Topological sorting on DAG
        Graph dag = new Graph(4, true);

        dag.addEdge(0, 1, 1);
        dag.addEdge(0, 2, 1);
        dag.addEdge(1, 3, 1);
        dag.addEdge(2, 3, 1);

        TopologicalSort.Result topo = TopologicalSort.sort(dag);

        check(topo.order != null && topo.order.size() == 4, "DAG order exists");

        int[] position = new int[4];

        for (int i = 0; i < topo.order.size(); i++) {
            position[topo.order.get(i)] = i;
        }

        for (Edge edge : dag.edges) {
            check(position[edge.from] < position[edge.to], "Valid topological order");
        }


        // Test 7: Cycle detection and SCC
        Graph directed = new Graph(5, true);

        directed.addEdge(0, 1, 1);
        directed.addEdge(1, 2, 1);
        directed.addEdge(2, 0, 1);
        directed.addEdge(2, 3, 1);

        check(TopologicalSort.sort(directed).order == null, "Cycle detection");

        Kosaraju.Result scc = Kosaraju.findSCC(directed);

        check(scc.components.size() == 3, "SCC count");

        // Each vertex must belong to exactly one SCC
        boolean[] seen = new boolean[5];

        for (ArrayList<Integer> component : scc.components) {
            for (int v : component) {
                check(!seen[v], "SCC duplicates");
                seen[v] = true;
            }
        }

        for (boolean value : seen) {
            check(value, "SCC missing vertex");
        }


        // Test 8: Dijkstra distances and paths
        Dijkstra.Result shortest = Dijkstra.shortestPaths(graph, 0);

        check(shortest.distances[3] == 4, "Dijkstra distance");

        check(
                shortest.getPathTo(3).equals(Arrays.asList(0, 2, 3)),
                "Dijkstra path"
        );

        System.out.println("All tests passed.");
    }
}