import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] tiles;
    private final int n;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }
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


    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if ((tiles[i][j] != 0) && (tiles[i][j + 1] != 0)) {
                    return (new Board(swappedArray(i, j, 0, 1)));
                }
            }
        }
        return (new Board(swappedArray(0, 0, 0, 1)));
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.tiles.length != that.tiles.length) return false;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
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

    // rowB and colB are offsets from rowA, colA

    private int[][] swappedArray(int rowA, int colA, int rowB, int colB) {
        int temp;
        int[][] tilesCopy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, tilesCopy[i], 0, tiles[i].length);
        }
        temp = tilesCopy[rowA][colA];
        tilesCopy[rowA][colA] = tilesCopy[rowA + rowB][colA + colB];
        tilesCopy[rowA + rowB][colA + colB] = temp;
        return tilesCopy;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> s = new Stack<>();
        int[] rowCol = findZero();
        if ((rowCol[0] - 1) >= 0) {
            Board neighbor = new Board(swappedArray(rowCol[0], rowCol[1], -1, 0));
            s.push(neighbor);
        }
        if ((rowCol[0] + 1) < tiles.length) {
            Board neighbor = new Board(swappedArray(rowCol[0], rowCol[1], +1, 0));
            s.push(neighbor);
        }
        if ((rowCol[1] - 1) >= 0) {
            Board neighbor = new Board(swappedArray(rowCol[0], rowCol[1], 0, -1));
            s.push(neighbor);
        }
        if ((rowCol[1] + 1) < tiles.length) {
            Board neighbor = new Board(swappedArray(rowCol[0], rowCol[1], 0, +1));
            s.push(neighbor);
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
        in = new In(args[1]);
        n = in.readInt();
        blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial2 = new Board(blocks);

        System.out.println(initial.equals(initial2));


    }
}
