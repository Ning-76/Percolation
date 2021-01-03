/* *****************************************************************************
 *  Name:              Ning Lang
 *  Coursera User ID:  ning.lang@outlook.com
 *  Last modified:     12/21/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int num;
    private final int gridsize;
    private final WeightedQuickUnionUF wqu;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        num = 0;
        if (n <= 0)
            throw new IllegalArgumentException("N should larger than 0!");
        gridsize = n;
        wqu = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        if (n > 1) {
            for (int i = 0; i < gridsize; i++) {
                for (int j = 0; j < gridsize; j++) {
                    grid[i][j] = false;
                    if (i == 0) {

                        wqu.union(transferToNum(i, j), 0);
                    }
                    if (i == gridsize - 1) {

                        wqu.union(transferToNum(i, j), gridsize * gridsize + 1);
                    }
                }
            }

        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row <= 0) && (col <= 0) && (row > gridsize) && (col > gridsize))
            throw new IllegalArgumentException("Out of bound!");
        int i = row, j = col;
        if (!grid[i - 1][j - 1]) {
            grid[i - 1][j - 1] = true;
            num++;
        }
        if (i - 1 > 0) {
            if (isOpen(i - 1, j)) {
                wqu.union(transferToNum(i - 1, j - 1), transferToNum(i - 2, j - 1));
            }
        }
        if ((i + 1 <= gridsize) && (isOpen(i + 1, j))) {
            wqu.union(transferToNum(row - 1, col - 1), transferToNum(row, col - 1));
        }
        if ((j + 1 <= gridsize) && (isOpen(i, j + 1))) {
            wqu.union(transferToNum(i - 1, j - 1), transferToNum(i - 1, j));
        }
        if (j - 1 > 0) {
            if (isOpen(i, j - 1)) {
                wqu.union(transferToNum(i - 1, j - 1), transferToNum(i - 1, j - 2));
            }
        }

    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row <= 0) && (col <= 0) && (row > gridsize) && (col > gridsize))
            throw new IllegalArgumentException("Out of bound!");
        return grid[row - 1][col - 1] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row <= 0) && (col <= 0) && (row > gridsize) && (col > gridsize))
            throw new IllegalArgumentException("Out of bound!");
        boolean isfull = false;
        int i = row, j = col;
        if (isOpen(i, j)) {
            isfull = ((i - 1 == 0) || (wqu.find(0) == wqu.find(transferToNum(i - 1, j - 1))));

        }

        return isfull;

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return num;
    }

    private int transferToNum(int row, int col) {

        return row * gridsize + col + 1;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean perco = false;
        if (gridsize == 1) {
            perco = isOpen(1, 1);
        }
        else {
            int p = gridsize * gridsize + 1;
            int q = 0;
            perco = (wqu.find(p) == wqu.find(q));
        }

    
        return perco;

    }


}




