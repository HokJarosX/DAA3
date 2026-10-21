package experiments;

import connectivity.Kosaraju;
import connectivity.TopologicalSort;
import graphs.Graph;
import graphs.GraphGenerator;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;

import java.util.Locale;

public class Benchmark {

    private static final int RUNS = 5;

    private static double averageMs(Runnable task) {

        // Warm-up
        task.run();

        long totalNs = 0;

        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();

            task.run();

            totalNs += System.nanoTime() - start;
        }

        return totalNs / (double) RUNS / 1_000_000;
    }

    public static void run() {

        int[] vertices = {10, 100, 1000};
        int[] edgeCounts = {30, 500, 5000};

        System.out.println(
                "algorithm,V,E,average_ms,edgeChecks,sortComparisons,"
                        + "findCalls,unionCalls,queueAdds,queuePolls,reversedEdges"
        );

        for (int i = 0; i < vertices.length; i++) {

            int v = vertices[i];
            int e = edgeCounts[i];

            // Generate reproducible graphs
            Graph undirected = GraphGenerator.connectedUndirected(v, e, 42);
            Graph dag = GraphGenerator.dag(v, e, 43);
            Graph cyclic = GraphGenerator.directedWithCycles(v, e, 44);


            // Kruskal
            Kruskal.Result kruskal = Kruskal.findMST(undirected);

            double kruskalMs = averageMs(
                    () -> Kruskal.findMST(undirected)
            );

            System.out.printf(
                    Locale.US,
                    "Kruskal,%d,%d,%.6f,%d,%d,%d,%d,0,0,0%n",
                    v, e, kruskalMs,
                    kruskal.edgeChecks,
                    kruskal.sortComparisons,
                    kruskal.findCalls,
                    kruskal.unionCalls
            );


            // Prim
            Prim.Result prim = Prim.findMST(undirected);

            double primMs = averageMs(
                    () -> Prim.findMST(undirected)
            );

            System.out.printf(
                    Locale.US,
                    "Prim,%d,%d,%.6f,%d,0,0,0,%d,%d,0%n",
                    v, e, primMs,
                    prim.edgeChecks,
                    prim.queueAdds,
                    prim.queuePolls
            );


            // Kosaraju
            Kosaraju.Result scc = Kosaraju.findSCC(cyclic);

            double sccMs = averageMs(
                    () -> Kosaraju.findSCC(cyclic)
            );

            System.out.printf(
                    Locale.US,
                    "Kosaraju,%d,%d,%.6f,%d,0,0,0,0,0,%d%n",
                    v, e, sccMs,
                    scc.edgeChecks,
                    scc.reversedEdges
            );


            // Topological sort: DAG
            TopologicalSort.Result topoDAG = TopologicalSort.sort(dag);

            double dagMs = averageMs(
                    () -> TopologicalSort.sort(dag)
            );

            System.out.printf(
                    Locale.US,
                    "TopologicalDAG,%d,%d,%.6f,%d,0,0,0,0,0,0%n",
                    v, e, dagMs,
                    topoDAG.edgeChecks
            );


            // Topological sort: graph with cycles
            TopologicalSort.Result topoCycle = TopologicalSort.sort(cyclic);

            double cycleMs = averageMs(
                    () -> TopologicalSort.sort(cyclic)
            );

            System.out.printf(
                    Locale.US,
                    "TopologicalCycle,%d,%d,%.6f,%d,0,0,0,0,0,0%n",
                    v, e, cycleMs,
                    topoCycle.edgeChecks
            );


            // Dijkstra
            Dijkstra.Result dijkstra = Dijkstra.shortestPaths(cyclic, 0);

            double dijkstraMs = averageMs(
                    () -> Dijkstra.shortestPaths(cyclic, 0)
            );

            System.out.printf(
                    Locale.US,
                    "Dijkstra,%d,%d,%.6f,%d,0,0,0,%d,%d,0%n",
                    v, e, dijkstraMs,
                    dijkstra.edgeChecks,
                    dijkstra.queueAdds,
                    dijkstra.queuePolls
            );
        }
    }
}