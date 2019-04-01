import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;                   // Size of the grid
    private int openSites;              // Total number of open sites
    private boolean[][] gridSites;      // Grid maintaining open status of sites
    private WeightedQuickUnionUF grid;          // UnionFind Object
    private int topRowIdentifier;
    private int botRowIdentifier;


    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("N has to be greater than 0");
        }
        size = n;
        openSites = 0;

        grid = new WeightedQuickUnionUF((n * n) + 1);
        gridSites = new boolean[n + 1][n + 1];
        for (int i = 1; i <= size - 1; i++) {           // Create components for top and bot and connect sites
            grid.union(i, i + 1);
            grid.union(getIndex(size, i), getIndex(size, i + 1));
        }
        topRowIdentifier = grid.find(1);
        botRowIdentifier = grid.find(getIndex(size, 1));

        for (int row = 1; row <= size; row++) {
            for (int col = 1; col <= size; col++) {
                gridSites[row][col] = false;
            }
        }
    }

    private int getIndex(int row, int col) {
        return ((row - 1) * size) + col;
    }

    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || col > size || row > size) {
            throw new java.lang.IllegalArgumentException("Row and Col have to be greater than 0");
        }
        if (gridSites[row][col]) {                              // site is already open
            return;
        }
        gridSites[row][col] = true;
        openSites++;
        if ((((row - 1) <= size) && ((row - 1) > 0)) && isOpen(row - 1, col)) {
            grid.union(getIndex(row - 1, col), getIndex(row, col));
        }
        if (((row + 1) <= size) && ((row + 1) > 0) && isOpen(row + 1, col)) {
            grid.union(getIndex(row + 1, col), getIndex(row, col));
        }
        if (((col + 1) <= size) && ((col + 1) > 0) && isOpen(row, col + 1)) {
            grid.union(getIndex(row, col + 1), getIndex(row, col));
        }
        if (((col - 1) <= size) && ((col - 1) > 0) && isOpen(row, col - 1)) {
            grid.union(getIndex(row, col - 1), getIndex(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || col > size || row > size) {
            throw new java.lang.IllegalArgumentException("Row and Col have to be greater than 0");
        }
        return gridSites[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || col > size || row > size) {
            throw new java.lang.IllegalArgumentException("Row and Col have to be greater than 0");
        }
        if (isOpen(row, col)) {
            return grid.connected(topRowIdentifier, getIndex(row, col));
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;

    }

    public boolean percolates() {
        return grid.connected(topRowIdentifier, botRowIdentifier);

    }

    public static void main(String[] args) {
        Percolation p = new Percolation(2);
        System.out.println(p.percolates());
        p.open(2, 1);
        System.out.println(p.percolates());
        p.open(1, 1);
        System.out.println(p.grid.find(1));
        System.out.println(p.percolates());
    }
}
