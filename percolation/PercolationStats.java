import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double CONSTANT_95 = 1.96;
    private int trials;
    private double[] percolationThresholds;
    private double mean;
    private double stddev;


    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
            throw new java.lang.IllegalArgumentException("n and trials have to be > 0");
        }
        trials = t;
        percolationThresholds = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation perc = new Percolation(n);
            double totalSites = n * n;
            double openSites;
            while (!perc.percolates()) {
                perc.open(getRandomRowCol(n + 1), getRandomRowCol(n + 1));
            }
            openSites = perc.numberOfOpenSites();
            percolationThresholds[trial] = openSites / totalSites;
        }
        mean = StdStats.mean(percolationThresholds);
        stddev = StdStats.stddev(percolationThresholds);
    }

    private int getRandomRowCol(int n) {
        int rowCol = StdRandom.uniform(n);
        while (rowCol == 0) {
            rowCol = StdRandom.uniform(n);
        }
        return rowCol;
    }

    public double mean() {

        return mean;
    }

    public double stddev() {
        if (trials == 1) {
            return Double.NaN;
        }
        return stddev;
    }

    public double confidenceLo() {
        double frac = (CONSTANT_95 * stddev) / Math.sqrt(trials);
        return (mean - frac);
    }

    public double confidenceHi() {
        double frac = (CONSTANT_95 * stddev) / Math.sqrt(trials);
        return (mean + frac);
    }

    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println("95% confidence interval = " + p.confidenceLo() + ", " + p.confidenceHi());
    }
}
