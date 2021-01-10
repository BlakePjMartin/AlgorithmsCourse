/* *****************************************************************************
 *  Name:              Blake Martin
 *  Last modified:     03/01/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // constant variables
    private static final double CONFIDENCE_95 = 1.96;

    // variables
    private final int trials;
    private final double[] thresholds;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // check the value of n and trials
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        // keep the number of trials
        this.trials = trials;

        // create the array storing all the threshold values
        thresholds = new double[trials];

        // perform the required number of trials
        Percolation curPerc;
        int arrID, row, col;
        for (int trialNum = 1; trialNum <= trials; trialNum++) {

            // reset the percolation
            curPerc = new Percolation(n);

            // loop until it percolates
            while (!curPerc.percolates()) {

                // get a new element to open
                arrID = StdRandom.uniform(n * n);

                // create row and cell indices from the random number
                row = arrID / n + 1;
                col = arrID % n + 1;

                // open the element
                curPerc.open(row, col);

            }

            // store the threshold value in the array of results
            thresholds[trialNum - 1] = (double) curPerc.numberOfOpenSites() / (n * n);

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(thresholds) - CONFIDENCE_95 * StdStats.stddev(thresholds) / Math
                .sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(thresholds) + CONFIDENCE_95 * StdStats.stddev(thresholds) / Math
                .sqrt(trials);
    }

    // test client
    public static void main(String[] args) {

        // get the two integer inputs
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        // create the PercolationStats class and execute the tests
        PercolationStats myPercStats = new PercolationStats(n, trials);

        // print out the stats
        StdOut.printf("mean                    = %f \n", myPercStats.mean());
        StdOut.printf("stddev                  = %f \n", myPercStats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f] \n", myPercStats.confidenceLo(),
                      myPercStats.confidenceHi());

    }

}
