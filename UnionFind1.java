/**
 * @author Dippin Chachan
 */
public class UnionFind1 extends UnionFind {

    public UnionFind1(int n) {
        super(n);
    }

    @Override
    public void union(int p, int q) {

        if (!connected(p, q)) {
            data[find(p)] = q;
            this.numConnectedComponents--;
        }

    }

}
