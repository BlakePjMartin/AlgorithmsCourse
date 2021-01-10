import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // pointers to the first and last nodes
    private Node first = null;
    private Node last = null;

    // keep track of the number of elements
    private int numElems;

    // create private node class
    // make this a doubly-linked list
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        numElems = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return numElems == 0;
    }

    // return the number of items on the deque
    public int size() {
        return numElems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        // check that a null item was not passed
        if (item == null) throw new IllegalArgumentException();

        // create a new node and add the item
        Node node = new Node();
        node.item = item;
        node.prev = null;

        // place the new node as the first
        node.next = first;

        // update the prev for the old first node
        if (numElems != 0) {
            first.prev = node;
        }

        // update where first points
        first = node;

        // increase the count of total elements
        numElems++;

        // check if this was the first element added
        // if so last also point to this
        if (numElems == 1) last = node;

    }

    // add the item to the back
    public void addLast(Item item) {
        // check that a null item was not passed
        if (item == null) throw new IllegalArgumentException();

        // create a new node and add the item
        Node node = new Node();
        node.item = item;
        node.next = null;

        // place the new node as the last
        node.prev = last;

        // update the next for the old last node
        if (numElems != 0) {
            last.next = node;
        }

        // update where last points
        last = node;

        // increase the count of total elements
        numElems++;

        // check if this was the first element added
        // if so first also point to this
        if (numElems == 1) first = node;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        // check if empty
        if (numElems == 0) throw new NoSuchElementException();

        // save the first element
        Item firstItem = first.item;

        // depending on the number of elements in the deque do a different action
        // change the pointer reference for the first item
        if (numElems == 1) {
            first = null;
            last = null;
        } else {
            first.next.prev = null;
            first = first.next;
        }

        // decrease the total number of elements
        numElems--;

        // return the item that was originally in the first position
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        // check if empty
        if (numElems == 0) throw new NoSuchElementException();

        // save the last element
        Item lastItem = last.item;

        // depending on the number of elements in the deque do a different action
        // change the pointer reference for the last item
        if (numElems == 1) {
            first = null;
            last = null;
        } else {
            last.prev.next = null;
            last = last.prev;
        }

        // decrease the total number of elements
        numElems--;

        // return the item that was originally in the last position
        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // private class for deque iterator
    private class DequeIterator implements Iterator<Item> {
        // start the iteration from the start
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            // not supported and not implemented
            throw new UnsupportedOperationException();
        }

        public Item next() {
            // check if there is an element to return
            if (current == null) throw new NoSuchElementException();

            // get the current item
            Item curItem = current.item;

            // move forward in the deque
            current = current.next;

            // return the item
            return curItem;
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        // create a deque of integers
        Deque<Integer> myDeque = new Deque<>();

        // check if empty
        System.out.println("isEmpty? " + myDeque.isEmpty());

        // add elements to the front and back
        // after adding each element print out the entire deque
        myDeque.addFirst(3);
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        myDeque.addFirst(2);
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        myDeque.addLast(4);
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        myDeque.addLast(5);
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        myDeque.addFirst(1);
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        // check the current size
        System.out.println("Current deque size is " + myDeque.size());

        // remove from the front
        System.out.println("Item removed from the front is " + myDeque.removeFirst());
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        System.out.println("Item removed from the front is " + myDeque.removeFirst());
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        System.out.println("Item removed from the front is " + myDeque.removeFirst());
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        // remove from the back
        System.out.println("Item removed from the back is " + myDeque.removeLast());
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();

        System.out.println("Item removed from the back is " + myDeque.removeLast());
        for (Integer i : myDeque) System.out.print(i);
        System.out.println();
    }

}
