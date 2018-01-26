/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 30-Dec-2017
 *  Last updated           : 30-Dec-2017
 * 
 *  Compilation            : use DrJava
 *  Execution              : java-algs4 PercolationStats 200 100
 *
 *  Purpose of the program : A program to estimate the value of the percolation threshold via Monte Carlo simulation.
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *  Score                  : 100/100
**********************************************************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // size of the grid
    private final int n;

    // Weighted Quick Union Find implementation
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    
    // array to store the grid in one dimension DS
    private boolean[] arr;

    // counter for number of open sites in the grid
    private int numOfOpenSites;

    /**
    * create n-by-n grid, with all sites blocked
    * Default Constructor for setting up the union find
    */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        numOfOpenSites = 0;
        arr = new boolean[n*n + 2];
        uf = new WeightedQuickUnionUF(n*n + 2);
        uf2 = new WeightedQuickUnionUF(n*n + 1);
    }
    
    private boolean isSiteInvalid(int row, int col) {
        return row < 1 || col < 1 || row > this.n || col > this.n;
    }

    private int getArrIndex(int row, int col) {
        return (row-1)*this.n + col;
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isSiteInvalid(row, col)) {
            throw new IllegalArgumentException();
        }
        
        int index = getArrIndex(row, col);
        if (!isOpen(row, col)) {
            numOfOpenSites++;
            this.arr[index] = true;
            
            if (row == 1) {
                uf.union(index, 0);
                uf2.union(index, 0);
            }
            
            if (row == this.n)
                uf.union(index, this.n * this.n + 1);
            
            // top site
            if (row > 1 && isOpen(row-1, col)) {
                uf.union(index, getArrIndex(row-1, col));
                uf2.union(index, getArrIndex(row-1, col));
            }
            
            // bottom site
            if (row < n && isOpen(row+1, col)) {
                uf.union(index, getArrIndex(row+1, col));
                uf2.union(index, getArrIndex(row+1, col));
            }
            
            // left site
            if (col > 1 && isOpen(row, col-1)) {
                uf.union(index, getArrIndex(row, col-1));
                uf2.union(index, getArrIndex(row, col-1));
            }
            
            // right site
            if (col < n && isOpen(row, col+1)) {
                uf.union(index, getArrIndex(row, col+1));
                uf2.union(index, getArrIndex(row, col+1));
            }
        }
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col)  {
        if (isSiteInvalid(row, col)) {
            throw new IllegalArgumentException();
        }
        return this.arr[getArrIndex(row, col)];
    }
    
    // is site (row, col) full
    public boolean isFull(int row, int col)  {
        if (isSiteInvalid(row, col)) {
            throw new IllegalArgumentException();
        }
        
        int index = getArrIndex(row, col);
        
        return uf2.connected(0, index);
    }
    
    // number of open sites
    public int numberOfOpenSites()    {
        return numOfOpenSites;
    }
    
    // does the system percolate
    public boolean percolates()  {
        return uf.connected(0, n*n + 1);
    }
}