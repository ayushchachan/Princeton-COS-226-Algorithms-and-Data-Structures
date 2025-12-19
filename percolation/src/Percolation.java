/*
By convention, the row and column indices are integers 
between 1 and n, where (1, 1) is the upper-left site:
Throw an IllegalArgumentException if any argument 
to open(), isOpen(), or isFull() is outside its prescribed range. 
Throw an IllegalArgumentException in the constructor if n ≤ 0.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Ayush Chachan
 */
public class Percolation {

    private final WeightedQuickUnionUF uf;     // S denotes the set of all elements
    private boolean[][] sites;          // 2d array, true when site is open
    private int numOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be <= 0");
        }
        uf = new WeightedQuickUnionUF(n * (n + 2));
        sites = new boolean[n][n];          // initially all sites are closed
        numOpen = 0;

        for (int i = 0; i < n - 1; i++) {
            uf.union(i, i + 1);
        }

        for (int i = n * n + n; i < n * n + 2 * n - 1; i++) {
            uf.union(i, i + 1);
        }
    }

    private void validate(int row, int col) {
        int n = sites.length;

        if (row < 1 || row > n) {
            throw new IllegalArgumentException("row is outside of range");
        }
        if (col < 1 || col > n) {
            throw new IllegalArgumentException("column is outside of range");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int i = row - 1;
        int j = col - 1;
        int ufIndex = i * sites.length + j + sites.length;

        if (sites[i][j]) {
            return;
        }

        sites[i][j] = true;

        this.numOpen++;
        //        if (this.percolates()) {
        //            System.out.println("system percolates at " + this.numberOfOpenSites() + " open sites");
        //        }

        if (row == 1) {
            uf.union(ufIndex, ufIndex - sites.length);
        }
        else if (row == sites.length) {
            uf.union(ufIndex, ufIndex + sites.length);
        }

        if (hasLeft(col) && isOpen(row, col - 1)) {
            uf.union(ufIndex, ufIndex - 1);
        }
        if (hasRight(col) && isOpen(row, col + 1)) {
            uf.union(ufIndex, ufIndex + 1);
        }
        if (hasTop(row) && isOpen(row - 1, col)) {
            uf.union(ufIndex, ufIndex - sites.length);
        }
        if (hasBottom(row) && isOpen(row + 1, col)) {
            uf.union(ufIndex, ufIndex + sites.length);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int i = row - 1;
        int j = col - 1;
        return sites[i][j];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int i = row - 1;
        int j = col - 1;
        int ufIndex = i * sites.length + j + sites.length;

        return isOpen(row, col) && (uf.find(ufIndex) == uf.find(0));
    }

    private boolean hasLeft(int col) {
        return col > 1;
    }

    private boolean hasRight(int col) {
        return col < sites.length;
    }

    private boolean hasTop(int row) {
        return row > 1;
    }

    private boolean hasBottom(int row) {
        return row < sites.length;
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = sites.length;
        return uf.find(0) == uf.find(n * n + 2 * n - 1);
    }

}
