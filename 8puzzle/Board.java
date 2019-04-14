import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] tiles;
    private final int n;

    public Board(int[][] blocks) {
        tiles = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i], 0, tiles[i], 0, blocks[i].length);
        }
        n = blocks.length;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int hamming = 0;                        // Including value for '0'
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (((i * n) + j + 1) != tiles[i][j]) {
                    hamming++;
                }
            }
        }

        return --hamming;

    }

    private int calculateManhattanDist(int sX, int sY, int dX, int dY) {
        return Math.abs(Math.abs(sX - dX) + Math.abs(sY - dY));
    }

    public int manhattan() {
        int manhattan = 0;                        // Including value for '0'
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((((i * n) + j + 1) != tiles[i][j]) && tiles[i][j] != 0) {
                    int row = (tiles[i][j] - 1) / n;
                    int col = (tiles[i][j] - 1) % n;
                    manhattan += calculateManhattanDist(i, j, row, col);
                }
            }
        }
        return manhattan;
    }

    public boolean isGoal() {
        if (hamming() == 0)
            return true;
        return false;
    }

    /*
        public Board twin() {

        }
    */
    public boolean equals(Object y) {
        return false;
    }

    private int[] findZero() {
        int[] rowCol = new int[2];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] == 0) {
                    rowCol[0] = i;
                    rowCol[1] = j;
                    return rowCol;
                }
        return rowCol;
    }

    private int[][] swappedArray(int zeroIndex[], int rowIndex, int colIndex) {
        int temp;
        int[][] tilesCopy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, tilesCopy[i], 0, tiles[i].length);
        }
        temp = tilesCopy[zeroIndex[0]][zeroIndex[1]];
        tilesCopy[zeroIndex[0]][zeroIndex[1]] = tilesCopy[zeroIndex[0] + rowIndex][zeroIndex[1] + colIndex];
        tilesCopy[zeroIndex[0] + rowIndex][zeroIndex[1] + colIndex] = temp;
        return tilesCopy;
    }

    public Iterable<Board> neighbours() {
        Stack<Board> s = new Stack<>();
        int[] rowCol = findZero();
        if ((rowCol[0] - 1) >= 0) {
            Board neighbour = new Board(swappedArray(rowCol, -1, 0));
            s.push(neighbour);
        }
        if ((rowCol[0] + 1) < tiles.length) {
            Board neighbour = new Board(swappedArray(rowCol, +1, 0));
            s.push(neighbour);
        }
        if ((rowCol[1] - 1) >= 0) {
            Board neighbour = new Board(swappedArray(rowCol, 0, -1));
            s.push(neighbour);
        }
        if ((rowCol[1] + 1) < tiles.length) {
            Board neighbour = new Board(swappedArray(rowCol, 0, +1));
            s.push(neighbour);
        }
        return s;

    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();

    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        for (Board b : initial.neighbours())
            System.out.println(b.toString());
    }
}
