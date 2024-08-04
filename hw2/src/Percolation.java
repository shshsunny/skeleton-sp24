import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int[][] DIRS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    private boolean[][] isOpen;
    private boolean[] isFull;
    private boolean[] isConnectedToBottom; // a concept corresponding to isFull
    private boolean percolates;
    private int n, numberOfOpenSites;
    private WeightedQuickUnionUF wqu;

    private int enc(int row, int col) {
        return row * n + col;
    }

    private boolean valid(int row, int col) {
        return 0 <= row && row < n && 0 <= col && col < n;
    }

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        isOpen = new boolean[N][N];
        isFull = new boolean[N * N];
        isConnectedToBottom = new boolean[N * N];
        percolates = false;
        n = N;
        wqu = new WeightedQuickUnionUF(N * N);
        numberOfOpenSites = 0;
    }

    public void open(int row, int col) {
        if (!isOpen[row][col]) {
            isOpen[row][col] = true;
            for (int i = 0; i < 4; ++i) {
                int nr = row + DIRS[i][0], nc = col + DIRS[i][1];
                if (valid(nr, nc) && isOpen[nr][nc]) {
                    // collect the attributes of this newly opened site and its neighbor sites(the
                    // former must be collected every loop to be up-to-date!)
                    boolean thisIsFull = isFull(row, col);
                    boolean thisIsConnectedToBottom = isConnectedToBottom(row, col);
                    boolean ithIsFull = isFull(nr, nc);
                    boolean ithIsConnectedToBottom = isConnectedToBottom(nr, nc);
                    wqu.union(enc(row, col), enc(nr, nc));
                    int newRoot = wqu.find(enc(row, col));
                    isFull[newRoot] = isFull[newRoot] || thisIsFull || ithIsFull; // update the representative of this
                                                                                  // set
                    // to be full!
                    isConnectedToBottom[newRoot] = isConnectedToBottom[newRoot] || thisIsConnectedToBottom
                            || ithIsConnectedToBottom;
                }
            }

            if (isFull(row, col) && isConnectedToBottom(row, col))
                percolates = true;
            numberOfOpenSites++;
        }
    }

    public boolean isFull(int row, int col) {
        if (!valid(row, col))
            throw new IndexOutOfBoundsException();
        if (row == 0 && isOpen(row, col))
            return isFull[wqu.find(enc(row, col))] = true;
        return isFull[wqu.find(enc(row, col))];
    }

    private boolean isConnectedToBottom(int row, int col) {
        if (!valid(row, col))
            throw new IndexOutOfBoundsException();
        if (row == n - 1 && isOpen(row, col))
            return isConnectedToBottom[wqu.find(enc(row, col))] = true;
        return isConnectedToBottom[wqu.find(enc(row, col))];
    }

    public boolean isOpen(int row, int col) {
        if (!valid(row, col))
            throw new IndexOutOfBoundsException();
        return isOpen[row][col];
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return percolates;
    }

}
