import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode best;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode> {
        public Board curBoard;
        public SearchNode prev;
        public int movesTillNode;

        public SearchNode(Board current, SearchNode previous, int moves) {
            curBoard = current;
            prev = previous;
            movesTillNode = moves;
        }

        public int compareTo(SearchNode n) {
            int thisToGoal = this.movesTillNode + this.curBoard.manhattan();
            int thatToGoal = n.movesTillNode + n.curBoard.manhattan();
            return thisToGoal - thatToGoal;
        }

    }

    public Solver(Board initial) {
        final MinPQ<SearchNode> pq = new MinPQ<>();
        final MinPQ<SearchNode> twinpq = new MinPQ<>();
        SearchNode bestTwin;
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        pq.insert(new SearchNode(initial, null, 0));
        twinpq.insert(new SearchNode(initial.twin(), null, 0));
        while (true) {
            best = pq.delMin();
            bestTwin = twinpq.delMin();
            if (best.curBoard.isGoal()) {
                solvable = true;
                break;
            }
            if (bestTwin.curBoard.isGoal()) {
                solvable = false;
                break;
            }
            for (Board neighbors : best.curBoard.neighbors()) {
                if (best.prev == null || !neighbors.equals(best.prev.curBoard)) {
                    pq.insert(new SearchNode(neighbors, best, best.movesTillNode + 1));
                }
            }
            for (Board neighbors : bestTwin.curBoard.neighbors()) {
                if (bestTwin.prev == null || !neighbors.equals(bestTwin.prev.curBoard)) {
                    twinpq.insert(new SearchNode(neighbors, bestTwin, bestTwin.movesTillNode + 1));
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
        SearchNode node = best;
        while (true) {
            solutionPath.push(node.curBoard);
            if (node.prev == null) {
                break;
            }
            node = node.prev;
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
