/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 30-Dec-2017
 *  Last updated           : 30-Dec-2017
 * 
 *  Compilation            : use DrJava
 *  Execution              : java-algs4 PercolationStats 200 100
 *
 *  Purpose of the program : A program to estimate the value of the percolation threshold via Monte Carlo simulation.
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *  Score                  : 100/100
**********************************************************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private double meanVal = -1;
    private double stddevVal = -1;
    
    private final double[] results;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }

            results[i] = (double) percolation.numberOfOpenSites() / (n*n);
        }
    }
   
    // sample mean of percolation threshold
    public double mean() {
        if (meanVal == -1)
            this.meanVal = StdStats.mean(results);
        return meanVal;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (stddevVal == -1)
            this.stddevVal = StdStats.stddev(results);
        return stddevVal;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        if (meanVal == -1)
            mean();
        if (stddevVal == -1)
            stddev();
        return meanVal - (CONFIDENCE_95 * stddevVal / Math.sqrt(results.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (meanVal == -1)
            mean();
        if (stddevVal == -1)
            stddev();
        return meanVal + (CONFIDENCE_95 * stddevVal / Math.sqrt(results.length));
    }

    // test client
    public static void main(String[] args)  {   
        int n      = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}