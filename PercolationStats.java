/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] threshold;
    private final double s;
    private double mean;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int t = trials;
        if (t <= 0) throw new IllegalArgumentException("T shall be > 0");
        threshold = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation per = new Percolation(n);
            int idx = 0;
            do {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                    idx++;
                }
            } while (!per.percolates());
            threshold[i] = (double) idx / (n * n);

        }
        double std = stddev();
        s = 1.96 * std / Math.sqrt(t);
    }

    // sample mean of percolation threshold
    public double mean() {

        mean = StdStats.mean(threshold);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double stddev;
        stddev = StdStats.stddev(threshold);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double lo;
        lo = mean - s;
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double hi;
        hi = mean + s;
        return hi;
    }


    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(200, 100);
        System.out.println("Mean                    = " + ps.mean());
        System.out.println("StdDev                  = " + ps.stddev());
        System.out.println(
                "95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());

    }

}
