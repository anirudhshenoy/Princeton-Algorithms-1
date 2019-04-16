import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<searchNode> pq = new MinPQ<>();
    private MinPQ<searchNode> twinpq = new MinPQ<>();
    private searchNode best;
    private boolean solvable = false;

    private class searchNode implements Comparable<searchNode> {
        public Board curBoard;
        public searchNode prev;
        public int movesTillNode;

        public searchNode(Board current, searchNode previous, int moves) {
            curBoard = current;
            prev = previous;
            movesTillNode = moves;
        }

        public int compareTo(searchNode n) {
            int thisToGoal = this.movesTillNode + this.curBoard.manhattan();
            int thatToGoal = n.movesTillNode + n.curBoard.manhattan();
            return thisToGoal - thatToGoal;
        }

    }

    public Solver(Board initial) {
        searchNode bestTwin;
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        pq.insert(new searchNode(initial, null, 0));
        twinpq.insert(new searchNode(initial.twin(), null, 0));
        while (true) {
            best = pq.delMin();
            bestTwin = twinpq.delMin();
            if (best.curBoard.isGoal()) {
                solvable = true;
                break;
            }
            if (bestTwin.curBoard.isGoal()) {
                break;
            }
            for (Board neighbours : best.curBoard.neighbours()) {
                if (best.prev == null || !neighbours.equals(best.prev.curBoard)) {
                    pq.insert(new searchNode(neighbours, best, best.movesTillNode + 1));
                }
            }
            for (Board neighbours : bestTwin.curBoard.neighbours()) {
                if (bestTwin.prev == null || !neighbours.equals(bestTwin.prev.curBoard)) {
                    twinpq.insert(new searchNode(neighbours, bestTwin, bestTwin.movesTillNode + 1));
                }
            }

        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (solvable) {
            return best.movesTillNode;
        }
        return -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> solutionPath = new Stack<>();
        while (true) {
            solutionPath.push(best.curBoard);
            if (best.prev == null) {
                break;
            }
            best = best.prev;
        }
        return solutionPath;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
