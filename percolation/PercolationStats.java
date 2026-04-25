
/*
 * Throw an IllegalArgumentException
 * in the constructor if either n ≤ 0 or trials ≤ 0.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * @author Ayush Chachan
 */
public class PercolationStats {

    private final double[] threshold;
    private final double CONSTANT = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and T values are out of bounds");
        }
        threshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation system1 = new Percolation(n);

            while (!system1.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                system1.open(x, y);
            }
            threshold[i] = system1.numberOfOpenSites() / ((double) n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double low = mean() - (CONSTANT * stddev() / Math.sqrt(threshold.length));
        return low;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double high = mean() + (CONSTANT * stddev() / Math.sqrt(threshold.length));
        return high;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        Stopwatch watch = new Stopwatch();
        double t = watch.elapsedTime();
        PercolationStats st = new PercolationStats(n, trials);
        System.out.println("mean                    = " + st.mean());
        System.out.println("stddev                  = " + st.stddev());
        System.out.println(
                "95% confidence interval = [" + st.confidenceLo() + ", " + st.confidenceHi() + "]");
        System.out.print("elapsed time     = " + t);
    }

}
