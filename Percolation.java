import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int topIndex;
    private final int btmIndex;
    private final int n;
    private int openCount;
    private final WeightedQuickUnionUF normalQU;
    private final WeightedQuickUnionUF backwashQU;
    private final boolean[] isOpen;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n < 0.");
        }
        this.n = n;
        topIndex = 0;
        btmIndex = n * n + 1;
        backwashQU = new WeightedQuickUnionUF(n * n + 2);
        normalQU = new WeightedQuickUnionUF(n * n + 1);
        isOpen = new boolean[n * n + 2];
        isOpen[topIndex] = true;
        isOpen[btmIndex] = true;
    }

    private int indexOf(int row, int col) {
        if (col < 1 || col > n) {
            throw new IndexOutOfBoundsException("Column input invalid.");
        }
        if (row < 1 || row > n) {
            throw new IndexOutOfBoundsException("Row input invalid.");
        }
        return (row - 1) * n + col;
    }

    public void open(int row, int col) {
        oOB(row, col);
        int tmpidx = indexOf(row, col);
        if (!isOpen[tmpidx]) {
            isOpen[tmpidx] = true;
            openCount++;

            if (row == 1) {
                backwashQU.union(tmpidx, topIndex);  // Top
                normalQU.union(tmpidx, topIndex);
            }
            if (row == n) {
                backwashQU.union(tmpidx, btmIndex);  // Bottom
            }
            canUN(row, col, row, col - 1);
            canUN(row, col, row, col + 1);
            canUN(row, col, row - 1, col);
            canUN(row, col, row + 1, col);
        }
    }

    private void canUN(int rowA, int colA, int rowB, int colB) {
        if (isInBounds(rowB, colB) && isOpen(rowB, colB)) {
            backwashQU.union(indexOf(rowA, colA), indexOf(rowB, colB));
            normalQU.union(indexOf(rowA, colA), indexOf(rowB, colB));
        }
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean isOpen(int row, int col) {
        oOB(row, col);
        return isOpen[indexOf(row, col)];
    }

    public boolean isFull(int row, int col) {
        oOB(row, col);
        return normalQU.find(topIndex) == normalQU.find(indexOf(row, col));
    }

    private void oOB(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("row or column is out of bounds");
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 1 && row <= n && col >= 1 && col <= n;
    }


    public boolean percolates() {
        int t = backwashQU.find(topIndex);
        int b = backwashQU.find(btmIndex);
        return t == b;
    }

    public static void main(String[] args) {
    }
}
