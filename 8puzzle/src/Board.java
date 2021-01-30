import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int n;  // the dimension of the board
    private final int[] tiles;  // the tiles of the board

    /***************************************************************
     // create a board from an n-by-n array of tiles,
     // where tiles[row][col] = tile at (row, col)
     ***************************************************************/
    public Board(int[][] tiles) {
        // get the dimension of the board
        n = tiles.length;

        // get the values of the tiles of the board
        this.tiles = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                this.tiles[i * n + j] = tiles[i][j];
        }
    }


    /***************************************************************
     // string representation of this board
     ***************************************************************/
    public String toString() {
        // use StringBuilder
        StringBuilder str = new StringBuilder();

        // add the dimension
        str.append(n);
        str.append("\n");

        // add the tile values
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str.append(" ");
                str.append(tiles[i * n + j]);
            }
            if (i != n - 1) str.append("\n");
        }

        // return the string
        return str.toString();
    }


    /***************************************************************
     // board dimension n
     ***************************************************************/
    public int dimension() {
        return this.n;
    }


    /***************************************************************
     // number of tiles out of place
     ***************************************************************/
    public int hamming() {
        // the goal is to have the array in order from 1 to n^2-1 (and zero as the final entry)
        int dist = 0;
        for (int i = 0; i < n * n - 1; i++)
            if (tiles[i] != (i + 1)) dist++;

        return dist;
    }


    /***************************************************************
     // sum of Manhattan distances between tiles and goal
     ***************************************************************/
    public int manhattan() {
        // the goal position of a tile A is row (A-1) / n + 1 and column (A-1) % n + 1
        int dist = 0;
        int tileVal, goalRow, goalCol, actualRow, actualCol;
        for (int i = 0; i < n * n; i++) {
            // the current row and column for where we are in the array
            actualRow = i / n + 1;
            actualCol = i % n + 1;

            // where the current tile value wants to go
            tileVal = tiles[i];
            if (tileVal == 0) continue;  // the empty space does not add to the Manhattan distance
            goalRow = ((tileVal - 1) / n + 1);
            goalCol = ((tileVal - 1) % n + 1);

            // add to the distance
            dist += Math.abs(goalRow - actualRow) + Math.abs(goalCol - actualCol);
        }

        return dist;
    }


    /***************************************************************
     // is this board the goal board?
     ***************************************************************/
    public boolean isGoal() {
        return hamming() == 0;
    }


    /***************************************************************
     // does this board equal y?
     ***************************************************************/
    public boolean equals(Object y) {
        // check if the input is null
        if (y == null) return false;

        // check that y is the correct class
        Board that;
        if (Board.class.isAssignableFrom(y.getClass())) {
            that = (Board) y;
        } else {
            return false;
        }

        // check that the dimensions are the same
        if (this.n != that.n) return false;

        return Arrays.equals(this.tiles, that.tiles);
    }


    /***************************************************************
     // all neighboring boards
     ***************************************************************/
    public Iterable<Board> neighbors() {

        // create a stack, add the neighbours to this, and return it
        Stack<Board> stack = new Stack<Board>();

        // variables used in the code
        int tempVal;

        // find the empty space (0 entry in the array)
        // calculate its row and column position
        int zeroPos = 0;
        while (tiles[zeroPos] != 0)
            zeroPos++;

        int zeroRow, zeroCol;
        zeroRow = zeroPos / n + 1;
        zeroCol = zeroPos % n + 1;

        // create a copy of the original tile order
        int[] origTiles = new int[n * n];
        for (int i = 0; i < n * n; i++)
            origTiles[i] = tiles[i];

        // create an array that will be used to generate the neighbour boards
        int[][] neighbourBoardTiles = new int[n][n];

        // add a neighbour if there is a tile to the left of the empty space
        if (zeroCol != 1) {
            // columns are next to each other in the tiles array
            tempVal = origTiles[zeroPos];
            origTiles[zeroPos] = origTiles[zeroPos - 1];
            origTiles[zeroPos - 1] = tempVal;

            // put the tiles in the correct input format for a new board
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    neighbourBoardTiles[i][j] = origTiles[i * n + j];

            // add the neighbour to the stack
            stack.push(new Board(neighbourBoardTiles));

            // undo the swap to get the original order back
            origTiles[zeroPos - 1] = origTiles[zeroPos];
            origTiles[zeroPos] = tempVal;
        }

        // add a neighbour if there is a tile to the right of the empty space
        if (zeroCol != n) {
            // columns are next to each other in the tiles array
            tempVal = origTiles[zeroPos];
            origTiles[zeroPos] = origTiles[zeroPos + 1];
            origTiles[zeroPos + 1] = tempVal;

            // put the tiles in the correct input format for a new board
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    neighbourBoardTiles[i][j] = origTiles[i * n + j];

            // add the neighbour to the stack
            stack.push(new Board(neighbourBoardTiles));

            // undo the swap to get the original order back
            origTiles[zeroPos + 1] = origTiles[zeroPos];
            origTiles[zeroPos] = tempVal;
        }

        // add a neighbour if there is a tile on the top of the empty space
        if (zeroRow != 1) {
            // columns are next to each other in the tiles array
            tempVal = origTiles[zeroPos];
            origTiles[zeroPos] = origTiles[zeroPos - n];
            origTiles[zeroPos - n] = tempVal;

            // put the tiles in the correct input format for a new board
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    neighbourBoardTiles[i][j] = origTiles[i * n + j];

            // add the neighbour to the stack
            stack.push(new Board(neighbourBoardTiles));

            // undo the swap to get the original order back
            origTiles[zeroPos - n] = origTiles[zeroPos];
            origTiles[zeroPos] = tempVal;
        }

        // add a neighbour if there is a tile on the top of the empty space
        if (zeroRow != n) {
            // columns are next to each other in the tiles array
            tempVal = origTiles[zeroPos];
            origTiles[zeroPos] = origTiles[zeroPos + n];
            origTiles[zeroPos + n] = tempVal;

            // put the tiles in the correct input format for a new board
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    neighbourBoardTiles[i][j] = origTiles[i * n + j];

            // add the neighbour to the stack
            stack.push(new Board(neighbourBoardTiles));

            // undo the swap to get the original order back
            origTiles[zeroPos + n] = origTiles[zeroPos];
            origTiles[zeroPos] = tempVal;
        }

        return stack;
    }


    /***************************************************************
     // a board that is obtained by exchanging any pair of tiles
     ***************************************************************/
    public Board twin() {
        /*
        // get two random integers representing the tiles to switch
        // make sure they are different and not the empty space

        int id1 = StdRandom.uniform(n * n);
        while (tiles[id1] == 0)
            id1 = StdRandom.uniform(n * n);

        int id2 = StdRandom.uniform(n * n);
        while (tiles[id2] == 0 || id1 == id2)
            id2 = StdRandom.uniform(n * n);
            */
        // the autograder is expecting twin to always output the same result
        // find the first non-zero entry in the array
        int id1 = 0;
        while (tiles[id1] == 0)
            id1++;

        // find the second non-zero entry in the array
        int id2 = id1 + 1;
        while (tiles[id2] == 0)
            id2++;

        // copy the board tiles
        int[] copyThisTiles = new int[n * n];
        for (int i = 0; i < n * n; i++)
            copyThisTiles[i] = tiles[i];

        // swap the two tiles
        int tempTile = copyThisTiles[id1];
        copyThisTiles[id1] = copyThisTiles[id2];
        copyThisTiles[id2] = tempTile;

        // create the array that will be used to generate the twin board
        int[][] twinBoardTiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                twinBoardTiles[i][j] = copyThisTiles[i * n + j];

        // create the new board and return it
        return new Board(twinBoardTiles);
    }


    /***************************************************************
     // unit testing (not graded)
     ***************************************************************/
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        // create the board
        Board initial = new Board(tiles);

        // test the string printout
        StdOut.println("My board:\n" + initial.toString());

        // get the dimension of the board
        StdOut.println("Dimension: " + initial.dimension());

        // get the hamming distance
        StdOut.println("Hamming distance: " + initial.hamming());

        // get the Manhattan distance
        StdOut.println("Manhattan distance: " + initial.manhattan());

        // check if it is the goal board
        StdOut.println("Goal board?: " + initial.isGoal());

        // check if the board equals itself, a null entry, a different board, or a different type
        int[][] otherBoardTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board otherBoard = new Board(otherBoardTiles);
        StdOut.println("Equals itself?: " + initial.equals(initial));
        StdOut.println("Equals other board?: " + initial.equals(otherBoard));

        // get a twin board and print it
        Board twinBoard = initial.twin();
        StdOut.println("Twin board:\n" + twinBoard.toString());

        // print out the neighbours
        StdOut.println("Neighbours:");
        for (Board board : initial.neighbors()) {
            StdOut.println(board.toString());
        }

    }

}
