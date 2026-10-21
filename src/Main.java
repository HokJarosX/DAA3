import connectivity.Kosaraju;
import connectivity.TopologicalSort;
import experiments.Benchmark;
import graphs.Edge;
import graphs.Graph;
import graphs.GraphGenerator;
import mst.Kruskal;
import mst.Prim;
import shortestpath.Dijkstra;
import tests.AlgorithmTests;

public class Main {

    public static void main(String[] args) {

        AlgorithmTests.run();

        example();

        Benchmark.run();
    }

    private static void example() {

        // Infrastructure network
        Graph roads = GraphGenerator.connectedUndirected(5, 7, 42);

        Kruskal.Result k = Kruskal.findMST(roads);
        Prim.Result p = Prim.findMST(roads);

        System.out.println(
                "Kruskal: connected=" + k.connected
                        + ", weight=" + k.totalWeight
        );

        System.out.println(
                "Prim: connected=" + p.connected
                        + ", weight=" + p.totalWeight
        );

        // Print MST edges
        for (Edge edge : k.mst) {
            System.out.println(
                    "MST edge: " + edge.from
                            + " - " + edge.to
                            + " (" + edge.weight + ")"
            );
        }

        // Shortest paths from vertex 0
        Dijkstra.Result shortest = Dijkstra.shortestPaths(roads, 0);

        for (int v = 0; v < roads.vertices; v++) {

            if (shortest.distances[v] == Long.MAX_VALUE) {
                System.out.println("To " + v + ": unreachable");
            } else {
                System.out.println(
                        "To " + v
                                + ": distance=" + shortest.distances[v]
                                + ", path=" + shortest.getPathTo(v)
                );
            }
        }

        // Directed graphs
        Graph dag = GraphGenerator.dag(5, 7, 43);
        Graph cyclic = GraphGenerator.directedWithCycles(5, 7, 44);

        System.out.println(
                "Topological order: " + TopologicalSort.sort(dag).order
        );

        System.out.println(
                "Cycle detected: "
                        + (TopologicalSort.sort(cyclic).order == null)
        );

        System.out.println(
                "SCC: " + Kosaraju.findSCC(cyclic).components
        );

        System.out.println();
    }
}