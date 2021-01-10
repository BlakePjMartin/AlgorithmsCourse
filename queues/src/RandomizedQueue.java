import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // array containing the elements and the current
    private Item[] arr;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        // check that a null item was not passed
        if (item == null) throw new IllegalArgumentException();

        // check if the array should be resized
        if (n == arr.length) resize(2 * arr.length);

        // add the item
        arr[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        // check if empty
        if (n == 0) throw new NoSuchElementException();

        // get a random integer between 0 and N-1 (the current number of elements in the array)
        int randInt = StdRandom.uniform(n);

        // get the item
        Item item = arr[randInt];

        // swap the item at this random integer position with the end item
        arr[randInt] = arr[--n];

        // avoid loitering
        arr[n] = null;

        // check if the array should be resized
        if (n > 0 && n == arr.length / 4) resize(arr.length / 2);

        // return the item
        return item;
    }

    // private function to resize the array when needed
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = arr[i];
        arr = copy;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        // check if empty
        if (n == 0) throw new NoSuchElementException();

        // get a random integer between 0 and N-1 (the current number of elements in the array)
        int randInt = StdRandom.uniform(n);

        // return the item
        return arr[randInt];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // private class for randomized queue iterator
    private class RandomizedQueueIterator implements Iterator<Item> {

        // get a copy of the array
        private Item[] curArr;
        private int curN;

        // constructor for the iterator
        public RandomizedQueueIterator() {
            curN = n;
            curArr = (Item[]) new Object[curN];

            // copy the contents of arr to curArr
            for (int i = 0; i < n; i++) {
                curArr[i] = arr[i];
            }

        }

        public boolean hasNext() {
            return curN != 0;
        }

        public void remove() {
            // not supported and not implemented
            throw new UnsupportedOperationException();
        }

        public Item next() {
            // check if there is an element to return
            if (curN == 0) throw new NoSuchElementException();

            // get a random integer between 0 and curN-1 (the current number of elements in the array)
            int randInt = StdRandom.uniform(curN);

            // get the item
            Item item = curArr[randInt];

            // swap the item at this random integer position with the end item
            curArr[randInt] = curArr[--curN];

            // return the item
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        // create a new randomized queue
        RandomizedQueue<Integer> myRQ = new RandomizedQueue<>();

        // check if the queue is empty
        System.out.println("isEmpty? " + myRQ.isEmpty());

        // add elements to the queue
        for (int i = 1; i < 10; i++) myRQ.enqueue(i);

        // check the current size
        System.out.println("Current queue size is " + myRQ.size());

        // perform a few samples
        System.out.println("Perform sampling:");
        for (int i = 1; i < 6; i++) System.out.println("\t" + myRQ.sample());

        // perform some iterations
        System.out.println("Perform some iterations:");

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        // dequeue some elements
        System.out.println("Item removed from queue is " + myRQ.dequeue());
        System.out.println("Item removed from queue is " + myRQ.dequeue());
        System.out.println("Item removed from queue is " + myRQ.dequeue());
        System.out.println("Item removed from queue is " + myRQ.dequeue());
        System.out.println("Item removed from queue is " + myRQ.dequeue());

        // perform a few samples
        System.out.println("Perform sampling:");
        for (int i = 1; i < 6; i++) System.out.println("\t" + myRQ.sample());

        // perform some iterations
        System.out.println("Perform some iterations:");

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

        System.out.print("\t");
        for (Integer i : myRQ) System.out.print(i + " ");
        System.out.println();

    }

}
