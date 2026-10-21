import graphs.Graph;
import graphs.Edge;

public class Main {
    public static void main(String[] args) {

        // false = undirected graph
        Graph graph = new Graph(4, false);

        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 3, 3);
        graph.addEdge(2, 3, 5);

        // Edge List
        for (Edge edge : graph.edges) {
            System.out.println(
                    edge.from + " -> " + edge.to +
                            " weight: " + edge.weight
            );
        }

        // Adjacency List
        for (int i = 0; i < graph.vertices; i++) {
            System.out.print(i + ": ");

            for (Edge edge : graph.adj.get(i)) {
                System.out.print(
                        "(" + edge.to + ", " + edge.weight + ") "
                );
            }

            System.out.println();
        }
    }
}