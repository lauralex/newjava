package com.company;

public class Percolation {
    private static final int top = 0;
    private boolean[][] ar;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufNoBot;
    private int bottom;
    private int length;

    /**
     * Create N by N grid with all sites blocked.
     *
     * @param N dimension of the grid
     */
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException();
        length = N;
        ar = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufNoBot = new WeightedQuickUnionUF(N * N + 1);
        bottom = N * N + 1;
    }

    /**
     * Open site (row i, column j) if it is not open already.
     *
     * @param i row
     * @param j column
     */
    public void open(int i, int j) {
        validate(i, j);
        if (!isOpen(i, j)) {
            ar[i - 1][j - 1] = true;
            checkNeighbors(i, j);
        }
    }

    /**
     * @param i row
     * @param j column
     * @return true if the site at row i and column j is open, false otherwise
     */
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return ar[i - 1][j - 1];
    }

    /**
     * @param i row
     * @param j column
     * @return true if the site at row i and column j is full, false otherwise
     */
    public boolean isFull(int i, int j) {
        validate(i, j);
        return ufNoBot.connected(top, to1D(i, j));
    }

    private void validate(int i, int j) {
        if (i < 1 || i > length || j < 1 || j > length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int to1D(int i, int j) {
        return length * (i - 1) + j;
    }

    private void checkNeighbors(int i, int j) {
        if (i == 1) {
            uf.union(to1D(i, j), top);
            ufNoBot.union(to1D(i, j), top);
        }
        if (i == length) {
            uf.union(to1D(i, j), bottom);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            uf.union(to1D(i, j), to1D(i, j - 1));
            ufNoBot.union(to1D(i, j), to1D(i, j - 1));
        }
        if (j < length && isOpen(i, j + 1)) {
            uf.union(to1D(i, j), to1D(i, j + 1));
            ufNoBot.union(to1D(i, j), to1D(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            uf.union(to1D(i, j), to1D(i - 1, j));
            ufNoBot.union(to1D(i, j), to1D(i - 1, j));
        }
        if (i < length && isOpen(i + 1, j)) {
            uf.union(to1D(i, j), to1D(i + 1, j));
            ufNoBot.union(to1D(i, j), to1D(i + 1, j));
        }
    }

    /**
     * Check if the system percolates.
     *
     * @return true if the system percolates, false otherwise
     */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

}
