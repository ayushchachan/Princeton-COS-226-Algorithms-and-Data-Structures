/**
 * @author Dippin Chachan
 */
public abstract class UnionFind {

    protected int[] data;

    protected int numConnectedComponents;

    public UnionFind(int n) {
        data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = i;
        }

        numConnectedComponents = n;
    }

    /**
     * Adds a connection between p and q
     */
    public abstract void union(int p, int q);

    /**
     * Component identifier for p, 0 <= p < n
     */
    public int find(int p) {
        validate(p);
        while (data[p] != p) p = data[p];
        return p;
    }

    /**
     * Returns true if p and q are in the same component
     */
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return find(p) == find(q);
    }

    /**
     * Returns the number of connected components
     */
    public int count() {
        return numConnectedComponents;
    }

    public void validate(int p) {
        if (p < 0 || p >= this.data.length) throw new IllegalArgumentException("index " + p + " is not valid");
    }
}
