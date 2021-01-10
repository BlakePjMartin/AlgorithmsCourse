/* *****************************************************************************
 *  Name:              Blake Martin
 *  Last modified:     03/01/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // variables
    private final int n;
    private final int virtualTopID;
    private final int virtualBottomID;

    // union-find
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufNoVirtualBottom;

    // counter for the total number of open sites
    private boolean[] openSites;
    private int totOpenSites = 0;


    /**
     * constructor for Percolation
     */
    public Percolation(int n) {
        // check the value of n
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        // store the n value passed
        this.n = n;

        // set the id for the virtual top and bottom nodes
        virtualTopID = n * n;
        virtualBottomID = n * n + 1;

        // create the union-find objects
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufNoVirtualBottom = new WeightedQuickUnionUF(n * n + 1);

        // create the array that keeps track of open sites
        openSites = new boolean[n * n];
        for (int i = 0; i < n * n; i++) openSites[i] = false;

    }


    /**
     * private function that checks if the row/col numbers are valid
     * returns the corresponding index into the id array
     */
    private int getArrayIndex(int row, int col) {
        // check that the row and column indices are valid
        checkIndex(row);
        checkIndex(col);

        // return the index into the array corresponding to row/col
        return (row - 1) * n + (col - 1);
    }


    /**
     * function that checks if an index is valid
     */
    private void checkIndex(int index) {
        if (index < 1 || index > n)
            throw new IllegalArgumentException();
    }


    /**
     * opens the site (row, col) if it is not open already
     * forms connections with surrounding neighbours if they are open
     */
    //
    public void open(int row, int col) {
        // if the site is already open then do nothing
        if (isOpen(row, col)) return;

        // add to the counter of open sites
        totOpenSites++;

        // open the site
        // if on the top or bottom row connect to the virtual top/bottom
        int arrID = getArrayIndex(row, col);
        openSites[arrID] = true;

        // separate if-statements because n might be equal to 1
        if (row == 1) {
            uf.union(arrID, virtualTopID);
            ufNoVirtualBottom.union(arrID, virtualTopID);
        }
        if (row == n) {
            uf.union(arrID, virtualBottomID);
        }

        // make connections to neighbours if they are open
        // left neighbour
        if (col > 1 && isOpen(row, col - 1)) {
            int arrID2 = getArrayIndex(row, col - 1);
            uf.union(arrID, arrID2);
            ufNoVirtualBottom.union(arrID, arrID2);
        }

        // right neighbour
        if (col < n && isOpen(row, col + 1)) {
            int arrID2 = getArrayIndex(row, col + 1);
            uf.union(arrID, arrID2);
            ufNoVirtualBottom.union(arrID, arrID2);
        }

        // top neighbour
        if (row > 1 && isOpen(row - 1, col)) {
            int arrID2 = getArrayIndex(row - 1, col);
            uf.union(arrID, arrID2);
            ufNoVirtualBottom.union(arrID, arrID2);
        }

        // bottom neighbour
        if (row < n && isOpen(row + 1, col)) {
            int arrID2 = getArrayIndex(row + 1, col);
            uf.union(arrID, arrID2);
            ufNoVirtualBottom.union(arrID, arrID2);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // get the index into the id array
        int arrID = getArrayIndex(row, col);

        // check if it is open
        return openSites[arrID];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // get the index into the id array
        int arrID = getArrayIndex(row, col);

        return ufNoVirtualBottom.find(arrID) == ufNoVirtualBottom.find(virtualTopID);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return totOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTopID) == uf.find(virtualBottomID);
    }

    // test client (optional)
    public static void main(String[] args) {
        // purposefully left empty
    }
}
