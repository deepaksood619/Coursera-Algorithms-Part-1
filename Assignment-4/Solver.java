/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 21-Jan-2017
 *  Last updated           : 21-Jan-2017
 * 
 *  Compilation            : javac-algs4 Solver.java
 *  Execution              : java-algs4 Solver
 *
 *  Purpose of the program : To find the solution to 8-puzzle problem using A* Search Algorithm
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *  Score                  : 90/100
**********************************************************************************************************************/
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private SearchNode goal;

    private class SearchNode {
        private int moves;
        private Board board;
        private SearchNode prev;
        // cache the manhattan distance to avoid recomputing its value
        private final int manhattanPriority;

        public SearchNode(Board initial) {
            moves = 0;
            prev = null;
            board = initial;
            manhattanPriority = board.manhattan();
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        PriorityOrder order = new PriorityOrder();
        MinPQ<SearchNode> PQ = new MinPQ<SearchNode>(order);
        MinPQ<SearchNode> twinPQ = new MinPQ<SearchNode>(order);
        SearchNode node = new SearchNode(initial);
        SearchNode twinNode = new SearchNode(initial);
        PQ.insert(node);
        twinPQ.insert(twinNode);

        SearchNode min = PQ.delMin();
        SearchNode twinMin = twinPQ.delMin();

        while (!min.board.isGoal() && !twinMin.board.isGoal()) {

            for (Board b : min.board.neighbors()) {
                if (min.prev == null || !b.equals(min.prev.board)) {   // check if move back this previous state
                    SearchNode n = new SearchNode(b);
                    n.moves = min.moves + 1;
                    n.prev = min;
                    PQ.insert(n);
                    }
            }
            
            for (Board b : twinMin.board.neighbors()) {
                if (twinMin.prev == null || !b.equals(twinMin.prev.board)) {
                    SearchNode n = new SearchNode(b);
                    n.moves = twinMin.moves + 1;
                    n.prev = twinMin;
                    twinPQ.insert(n);
                    }
            }
             
            min = PQ.delMin();
            twinMin = twinPQ.delMin();
         }
         if (min.board.isGoal())  goal = min;
         else                     goal = null; 
    }

    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int pa = a.manhattanPriority + a.moves;
            int pb = b.manhattanPriority + b.moves;
            if (pa > pb) return 1;
            if (pa < pb) return -1;
            else return 0;
        }
    }

    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return goal != null;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (!isSolvable()) return -1;
        else return goal.moves;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> s = new Stack<Board>();
        for (SearchNode n = goal; n != null; n = n.prev)
            s.push(n.board);
        return s;
    }

    public static void main(String[] args) {

        // create initial board from file
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