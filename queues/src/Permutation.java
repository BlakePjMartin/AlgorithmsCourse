import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        // get the number of outputs the permutation should return
        int numOut = Integer.parseInt(args[0]);

        // store the entries in a randomized queue
        RandomizedQueue<String> myRQ = new RandomizedQueue<>();

        // continue reading the input until it is empty
        // store each entry in the randomized queue
        String str;
        while (!StdIn.isEmpty()) {
            // get the next string and add it to the randomized queue
            str = StdIn.readString();
            myRQ.enqueue(str);
        }

        // return elements from the queue randomly
        for (int i = 0; i < numOut; i++) StdOut.println(myRQ.dequeue());

    }

}
