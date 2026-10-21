import graphs.Graph;
import graphs.GraphGenerator;
import mst.Kruskal;
import mst.Prim;
import connectivity.TopologicalSort;
import connectivity.Kosaraju;
import shortestpath.Dijkstra;

public class Main {

    public static void main(String[] args) {

        testKruskal();
        testPrim();
        testTopologicalSort();
        testKosaraju();
        testDijkstra();
        Graph graph = GraphGenerator.generate(10, 30);

        System.out.println("Vertices: " + graph.vertices);
        System.out.println("Edges: " + graph.edges.size());
    }


    // 1. Kruskal
    public static void testKruskal() {
        System.out.println("\n===== KRUSKAL =====");

        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(2, 1, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 4, 2);

        Kruskal.findMST(graph);
    }

    // 2. Prim
    public static void testPrim() {
        System.out.println("\n===== PRIM =====");

        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(2, 1, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 4, 2);

        Prim.findMST(graph);
    }

    // 3. Topological Sort
    public static void testTopologicalSort() {
        System.out.println("\n===== TOPOLOGICAL SORT =====");

        Graph graph = new Graph(6, true);

        graph.addEdge(5, 2, 1);
        graph.addEdge(5, 0, 1);
        graph.addEdge(4, 0, 1);
        graph.addEdge(4, 1, 1);
        graph.addEdge(2, 3, 1);
        graph.addEdge(3, 1, 1);

        System.out.println(
                "Topological order: "
                        + TopologicalSort.sort(graph)
        );
    }

    // 4. Kosaraju — Strongly Connected Components
    public static void testKosaraju() {
        System.out.println("\n===== KOSARAJU =====");

        Graph graph = new Graph(5, true);

        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 1);
        graph.addEdge(2, 0, 1);
        graph.addEdge(1, 3, 1);
        graph.addEdge(3, 4, 1);

        System.out.println(
                "Strongly connected components: "
                        + Kosaraju.findSCC(graph)
        );
    }

    // 5. Dijkstra
    public static void testDijkstra() {
        System.out.println("\n===== DIJKSTRA =====");

        Graph graph = new Graph(5, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(2, 1, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 4, 2);

        int source = 0;

        Dijkstra.Result result =
                Dijkstra.shortestPaths(graph, source);

        for (int i = 0; i < graph.vertices; i++) {
            System.out.println(
                    "Vertex " + i
                            + ", distance: " + result.distances[i]
                            + ", path: " + result.getPathTo(i)
            );
        }
    }
}