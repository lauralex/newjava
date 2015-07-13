package com.company;

public class PercolationStats {
    private final int tempts;
    private final double[] IDArray;

    /**
     * Constructor that uses the Percolation class.
     *
     * @param N grid size
     * @param T number of experiments
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        tempts = T;
        IDArray = new double[T];
        int grid = N * N;
        for (int j = 0; j < T; j++) {
            double countSites = 0;
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    countSites++;
                }
            }
            IDArray[j] = countSites / grid;
        }
    }

    public static void main(String[] args) {
        Stopwatch timer = new Stopwatch();
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
        System.out.println(timer.elapsedTime());
    }

    /**
     * @return the average of the thresholds of all the experiments
     */
    public double mean() {
        return StdStats.mean(IDArray);
    }

    /**
     * @return the sample standard deviation
     */
    public double stddev() {
        return StdStats.stddev(IDArray);
    }

    /**
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(tempts));
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(tempts));
    }

}
