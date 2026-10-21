# DAA3
# OVERVIEW
This project presents five graph algorithms for working with graphs in a Smart City infrastructure system.
Kruskal and Prim algorithms are used to find MST. Kosaraju's algorithm finds SCC, Topological Sort determines 
the correct order of dependent tasks and detects cycles. 
Dijkstra's algorithm finds the shortest paths from a starting vertex to all other reachable vertices
These algorithms can be used to analyze city connections, plan infrastructure, and find efficient routes.
# IMPLEMENTATION

### Kruskal's Algorithm
Kruskal's algorithm works by sorting all edges by their weights. 
Then, it checks each edge to determine whether it can be added to the Minimum Spanning Tree
without creating a cycle. 
The algorithm uses DSU to check whether two vertices already belong to the same connected component.

### Prim's Algorithm
Prim's algorithm is also used to find a Minimum Spanning Tree, but it does not use DSU.
Instead, it uses an adjacency list, a visited array, and a priority queue. 
The algorithm starts from one vertex and selects the smallest available edge that connects the current tree 
to a new vertex.

### DSU
DSU is used in Kruskal's algorithm to check whether two vertices already belong to the same connected component. 
This helps prevent cycles when building the MST.
The implementation uses two optimizations:
Path Compression makes future searches faster by connecting visited vertices directly to their root.
Union by Size connects the smaller component to the larger one, helping keep the trees shallow.
These optimizations make DSU operations more efficient.

### Kosaraju's Algorithm
Kosaraju's algorithm is used to find Strongly Connected Components (SCC) in a directed graph. 
It uses DFS twice. First, DFS determines the order in which vertices finish processing. 
Then, the graph's edges are reversed, and DFS is performed again in the calculated order to identify strongly 
connected components.

### Topological Sort
Topological Sort is used to find the correct order of vertices in a Directed Acyclic Graph (DAG).
The implementation uses Kahn's algorithm, which calculates the in-degree of each vertex and starts with 
vertices that have an in-degree of zero. 
After processing a vertex, the in-degrees of its neighbors are decreased. 
If not all vertices can be processed, the graph contains a cycle.

### Dijkstra's Algorithm
Dijkstra's algorithm is used to find the shortest paths from a starting vertex to all other reachable vertices 
in a weighted graph. 
It uses a priority queue to select the vertex with the smallest current distance. 
Then, it checks neighboring vertices and updates their distances if a shorter path is found. 
The algorithm works with non-negative edge weights.


# RESULTS AND DISCUSSION
Analyzing the table and graphs, we can see that the execution time of most algorithms increases as the 
number of vertices and edges grows. 
Kruskal's and Prim's algorithms show similar results for large graphs, while Prim's algorithm is faster for 
the graph with 100 vertices. 
Kosaraju's algorithm and Topological Sort also show an increase in execution time. 
However, Topological Sort on cyclic graphs takes less time because the algorithm stops without processing 
all vertices. Overall, the experimental results show how graph size affects algorithm performance.

# TIME COMPLEXITY

Kruskal — O(E log E)
Sorting edges by weight.

Prim — O(E log E)
Using a priority queue to select minimum edges.

Kosaraju — O(V + E)
Two DFS passes and graph reversal.

Topological Sort — O(V + E)
Processing each vertex and edge once.

Dijkstra — O((V + E) log(V + E))
Using a priority queue to update shortest distances.

# EXPERIMENTAL SETUP
The algorithms were tested on graphs with 10, 100, and 1000 vertices and 30, 500, and 5000 edges.
Each algorithm was executed 5 times, and the average execution time was measured in milliseconds. 
Fixed seeds were used to generate reproducible graphs.

# RESULTS
The execution times and operation counts are available in benchmark.csv.

# TESTING

The algorithms were tested on small graphs to check their correctness. 
Kruskal and Prim produced the same MST weight. 
Topological Sort detected cycles, and Dijkstra was tested for shortest paths.

# CONCLUSION

All five graph algorithms were implemented and tested successfully. 
The experiments showed that execution time generally increases with graph size. 
Each algorithm solves a different graph problem.