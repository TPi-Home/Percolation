import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private final double[] fr;
    private final double c = 1.96;
    
    public PercolationStats(int n, int test) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0.");
        }
        if (test <= 0) {
            throw new IllegalArgumentException("trials <= 0.");
        }
        fr = new double[test];
        for (int x = 0; x < test; x++) {
            Percolation percolation = new Percolation(n);
            int openedSites = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;  // base-1
                int col = StdRandom.uniformInt(n) + 1;  // base-1
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openedSites++;
                }
            }
            fr[x] = openedSites * 1.0 / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(fr);
    }

    public double stddev() {
        return StdStats.stddev(fr);
    }

    public double confidenceLo() {
        return mean() - c * stddev() / Math.sqrt(fr.length);
    }

    public double confidenceHi() {
        return mean() + c * stddev() / Math.sqrt(fr.length);
    }

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = ["
                               + stats.confidenceLo() + ", "
                               + stats.confidenceHi() + "]");
    }
}
