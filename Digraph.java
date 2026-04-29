import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.In;

public class Digraph {
    
    private final int V;                // number of vertices
    private int E;                      // number of edges
    private List<Integer>[] adj;        // adjacency lists

    // @SuppressWarnings("unchecked") // Suppress warning for the cast
    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = (List<Integer>[]) new ArrayList[V];              // Create the array as a raw ArrayList array, then cast

        for (int i = 0; i < adj.length; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public Digraph(In in) {
        this(in.readInt());
        int E = in.readInt();

        for (int i = 0; i < E; i++) {
            // vertex v-->w
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse() {
        Digraph R = new Digraph(this.V);

        for (int u = 0; u < adj.length; u++) {
            for (int v : adj[u]) {
                R.addEdge(v, u);
            }
        }

        return R;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges\n");

        for (int u = 0; u < adj.length; u++) {
            s.append(u + ": " + adj[u].toString() + "\n");
        }

        return s.toString();
    }
}
