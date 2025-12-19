/**
 * @author Ayush Chachan
 */
public class QuickFindUF {
    private int count;          // number of connected components
    private int[] id;           // id[i] = component identifier of i


    /**
     * Initialize N empty sets
     */
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        count = N;
    }

    /**
     * Returns the number of sets.
     */
    public int count() {
        return this.count;
    }

    public int find(int p) {
        validate(p);
        return id[p];
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
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
     * Adds a connection between p and q
     */
    public void union(int p, int q) {
        //        System.out.println("union is called with p = " + p + ", q = " + q);
        //        System.out.println("id = " + java.util.Arrays.toString(id));
        int pID = id[p];
        int qID = id[q];

        if (pID == qID) return;

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) id[i] = qID;
        }
        count--;
        //        System.out.println("id = " + java.util.Arrays.toString(id));
        //        System.out.println("------------method terminated--------------");
    }

}
