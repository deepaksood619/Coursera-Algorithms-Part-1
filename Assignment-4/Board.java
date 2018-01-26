/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 21-Jan-2017
 *  Last updated           : 21-Jan-2017
 * 
 *  Compilation            : javac-algs4 Board.java
 *  Execution              : java-algs4 Board
 *
 *  Purpose of the program : To find the solution to 8-puzzle problem using A* Search Algorithm
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *  Score                  : 90/100
**********************************************************************************************************************/
import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int size;
    private int[] board;

    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        size = blocks[0].length;
        board = new int[size * size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                board[i * size + j] = blocks[i][j];
    }

    /**
     * private constructor useful in twin()
     */
    private Board(int[] board) {
        size = (int) Math.sqrt(board.length);
        this.board = new int[board.length];
        for (int i = 0; i < board.length; i++)
            this.board[i] = board[i];
    }

    /**
     * board dimension n
     */
    public int dimension() {
        return size;
    }

    /**
     * number of blocks out of place
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < size * size; i++)
            if (board[i] != i + 1 && board[i] != 0)
                count++;
        return count;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < size * size; i++)
            if (board[i] != i + 1 && board[i] != 0)
                sum += manhattan(board[i], i);
        return sum;
    }

    /**
     * return manhattan distance of a misplaced block
     */
    private int manhattan(int goal, int current) {
        int row, col;
        row = Math.abs((goal - 1) / size - current / size);
        col = Math.abs((goal - 1) % size - current % size);
        return row + col;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        for (int i = 0; i < size * size - 1; i++)
            if (board[i] != i + 1)
                return false;
        return true;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        Board twin;
        if (size == 1) return null;
        twin = new Board(board);

        if (board[0] != 0 && board[1] != 0)
            exch(twin, 0, 1);
        else
            exch(twin, size, size + 1);
        return twin;
    }

    /**
     * exchange two elements in the array
     */
    private Board exch(Board a, int i, int j) {
        int temp = a.board[i];
        a.board[i] = a.board[j];
        a.board[j] = temp;
        return a;
    }

    /**
     * does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        return Arrays.equals(this.board, that.board);
    }

    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        int index = 0;
        boolean found = false;
        Board neighbor;
        Queue<Board> q = new Queue<Board>();

        for (int i = 0; i < board.length; i++)
            if (board[i] == 0) {
                index = i;
                found = true;
                break;
            }

        if (!found) return null;

        // exchange with upper block
        if (index / size != 0) {
            neighbor = new Board(board);
            exch(neighbor, index, index - size);
            q.enqueue(neighbor);
        }

        // exchange with lower block
        if (index / size != (size - 1)) {
            neighbor = new Board(board);
            exch(neighbor, index, index + size);
            q.enqueue(neighbor);
        }

        // exchange with left block
        if ((index % size) != 0) {
            neighbor = new Board(board);
            exch(neighbor, index, index - 1);
            q.enqueue(neighbor);
        }

        // exchange with right block
        if ((index % size) != size - 1) {
            neighbor = new Board(board);
            exch(neighbor, index, index + 1);
            q.enqueue(neighbor);
        }

        return q;
    }

    /**
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                s.append(String.format("%2d ", board[i * size + j]));
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * unit tests
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println(initial);
    }
}