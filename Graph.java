import java.util.*;

/**
 * MODULE 5: VISUALIZATION — Weighted Graph
 * Models a logistics / delivery network.
 *
 * Algorithms:
 *   BFS      → shortest hops, level-order traversal  O(V+E)
 *   DFS      → deep traversal, cycle detection        O(V+E)
 *   Dijkstra → shortest weighted path                 O((V+E) log V)
 *
 * Representation: Adjacency List
 */
class Graph {

    private static class Edge {
        int dest, weight;
        Edge next;
        Edge(int d, int w, Edge n) { dest = d; weight = w; next = n; }
    }

    private final Edge[] adj;
    private final int    V;

    public Graph(int vertices) {
        this.V   = vertices;
        this.adj = new Edge[vertices];
    }

    /** Add undirected weighted edge */
    public void addEdge(int u, int v, int w) {
        adj[u] = new Edge(v, w, adj[u]);
        adj[v] = new Edge(u, w, adj[v]);
    }

    /** Add directed weighted edge */
    public void addDirectedEdge(int u, int v, int w) {
        adj[u] = new Edge(v, w, adj[u]);
    }

    // ── BFS ─────────────────────────────────────────────────
    public void bfs(int start, String[] labels) {
        boolean[] visited = new boolean[V];
        Queue<Integer> q  = new LinkedList<>();
        visited[start] = true;
        q.add(start);
        while (!q.isEmpty()) {
            int node = q.poll();
            System.out.print(labels[node] + " -> ");
            for (Edge e = adj[node]; e != null; e = e.next)
                if (!visited[e.dest]) { visited[e.dest] = true; q.add(e.dest); }
        }
    }

    // ── DFS ─────────────────────────────────────────────────
    public void dfs(int start, String[] labels) {
        boolean[] visited = new boolean[V];
        dfsHelper(start, visited, labels);
    }

    private void dfsHelper(int node, boolean[] visited, String[] labels) {
        visited[node] = true;
        System.out.print(labels[node] + " -> ");
        for (Edge e = adj[node]; e != null; e = e.next)
            if (!visited[e.dest]) dfsHelper(e.dest, visited, labels);
    }

    // ── Dijkstra ────────────────────────────────────────────
    public void dijkstra(int src, String[] labels) {
        int[]     dist    = new int[V];
        int[]     prev    = new int[V];
        boolean[] settled = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, src});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int d = cur[0], u = cur[1];
            if (settled[u]) continue;
            settled[u] = true;
            for (Edge e = adj[u]; e != null; e = e.next) {
                if (!settled[e.dest] && d + e.weight < dist[e.dest]) {
                    dist[e.dest] = d + e.weight;
                    prev[e.dest] = u;
                    pq.offer(new int[]{dist[e.dest], e.dest});
                }
            }
        }

        for (int i = 0; i < V; i++) {
            String path = buildPath(prev, i, labels);
            System.out.printf("  %-12s -> %-12s : dist=%-5d path=%s%n",
                              labels[src], labels[i], dist[i], path);
        }
    }

    private String buildPath(int[] prev, int node, String[] labels) {
        if (prev[node] == -1) return labels[node];
        return buildPath(prev, prev[node], labels) + " -> " + labels[node];
    }

    // ── Cycle Detection ─────────────────────────────────────
    public boolean hasCycle() {
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++)
            if (!visited[i] && cycleCheck(i, visited, -1)) return true;
        return false;
    }

    private boolean cycleCheck(int node, boolean[] visited, int parent) {
        visited[node] = true;
        for (Edge e = adj[node]; e != null; e = e.next) {
            if (!visited[e.dest]) { if (cycleCheck(e.dest, visited, node)) return true; }
            else if (e.dest != parent) return true;
        }
        return false;
    }

    // ── Display ─────────────────────────────────────────────
    public void displayGraph(String[] labels) {
        for (int i = 0; i < V; i++) {
            System.out.print("  " + labels[i] + ": ");
            for (Edge e = adj[i]; e != null; e = e.next)
                System.out.print("[" + labels[e.dest] + ", w=" + e.weight + "] ");
            System.out.println();
        }
    }
}
