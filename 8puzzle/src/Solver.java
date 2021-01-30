import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.io.Serializable;
import java.util.Comparator;

public class Solver {
    private final int moves;
    private final Board[] solution;

    /***************************************************************
     // find a solution to the initial board (using the A* algorithm)
     ***************************************************************/
    public Solver(Board initial) {
        // check if a null was given
        if (initial == null) throw new IllegalArgumentException();

        // create two priority queues
        // one for the initial board and one for a twin
        MinPQ<SearchNode> gameOrigTree = new MinPQ<>(priority());
        MinPQ<SearchNode> gameTwinTree = new MinPQ<>(priority());

        // create variables that will be used during the search iterations
        Board curSearchBoard, prevSearchBoard;
        SearchNode curSearchNode, prevSearchNode;

        // add the original board to the game tree
        curSearchBoard = initial;
        curSearchNode = new SearchNode(curSearchBoard, 0, null);
        gameOrigTree.insert(curSearchNode);

        // add the twin board to the twin game tree
        curSearchBoard = initial.twin();
        curSearchNode = new SearchNode(curSearchBoard, 0, null);
        gameTwinTree.insert(curSearchNode);

        // continue iterating until one of the goal boards is found
        while (true) {
            /***************************************************************
             // ORIGINAL GAME TREE
             ***************************************************************/
            // get the current search node for the original game tree
            curSearchNode = gameOrigTree.delMin();
            curSearchBoard = curSearchNode.getBoard();

            // check if this is the goal board for the original game tree
            if (curSearchBoard.isGoal()) {
                // set class variables since it is solvable
                moves = curSearchNode.getMoves();

                // add the solution by going back through the history of the game tree
                solution = new Board[moves + 1];
                for (int i = 0; i < moves + 1; i++) {
                    solution[i] = curSearchBoard;
                    prevSearchNode = curSearchNode.getPrevSearchNode();
                    if (!(prevSearchNode == null)) {
                        curSearchNode = prevSearchNode;
                        curSearchBoard = curSearchNode.getBoard();
                    }
                }

                break;
            }

            // get the previous search node and board for the original game tree
            prevSearchNode = curSearchNode.getPrevSearchNode();
            prevSearchBoard = (prevSearchNode != null) ? prevSearchNode.getBoard() : null;

            // get all the neighbours of the current search board for the original game tree
            // add all the neighbours to the original priority queue (except for the previous board)
            for (Board board : curSearchBoard.neighbors()) {
                if (!board.equals(prevSearchBoard)) {
                    gameOrigTree.insert(new SearchNode(board, 1 + curSearchNode.getMoves(), curSearchNode));
                }
            }

            /***************************************************************
             // TWIN GAME TREE
             ***************************************************************/
            // get the current search node for the twin game tree
            curSearchNode = gameTwinTree.delMin();
            curSearchBoard = curSearchNode.getBoard();

            // check if this is the goal board for the twin game tree
            if (curSearchBoard.isGoal()) {
                moves = -1;
                solution = null;
                break;
            }

            // get the previous search node and board for the twin game tree
            prevSearchNode = curSearchNode.getPrevSearchNode();
            prevSearchBoard = (prevSearchNode != null) ? prevSearchNode.getBoard() : null;

            // get all the neighbours of the current search board for the twin game tree
            // add all the neighbours to the original priority queue (except for the previous board)
            for (Board board : curSearchBoard.neighbors()) {
                if (!board.equals(prevSearchBoard)) {
                    gameTwinTree.insert(new SearchNode(board, 1 + curSearchNode.getMoves(), curSearchNode));
                }
            }


        }

    }

    private Comparator<SearchNode> priority() {
        return new ByPriority();
    }

    private static class ByPriority implements Comparator<SearchNode>, Serializable {
        private static final long serialVersionUID = 1;

        public int compare(SearchNode sn1, SearchNode sn2) {
            // test first with just the Manhattan priority function
            return sn1.getManhattanPriority() - sn2.getManhattanPriority();
        }
    }

    private static class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode prevSearchNode;
        private final int manhattanPriority;

        public SearchNode(Board board, int moves, SearchNode prevSearchNode) {
            this.board = board;
            this.moves = moves;
            this.prevSearchNode = prevSearchNode;
            this.manhattanPriority = board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPrevSearchNode() {
            return prevSearchNode;
        }

        public int getManhattanPriority() {
            return manhattanPriority;
        }
    }


    /***************************************************************
     // is the initial board solvable? (see below)
     ***************************************************************/
    public boolean isSolvable() {
        return moves != -1;
    }


    /***************************************************************
     // min number of moves to solve initial board; -1 if unsolvable
     ***************************************************************/
    public int moves() {
        return moves;
    }


    /***************************************************************
     // sequence of boards in a shortest solution; null if unsolvable
     ***************************************************************/
    public Iterable<Board> solution() {
        // check if the board was solvable
        if (!isSolvable()) return null;

        // create a queue, add the solution to it, and return it
        Stack<Board> boardStack = new Stack<>();

        for (Board board : solution) boardStack.push(board);

        return boardStack;
    }


    /***************************************************************
     // test client
     ***************************************************************/
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);


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
