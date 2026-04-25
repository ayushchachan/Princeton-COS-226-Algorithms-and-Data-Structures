/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced “deck”) is a generalization of a
 * stack
 * and a queue that supports adding and removing items from either the front or
 * the back of the data
 * structure.
 * structure of list looks like
 * <p>
 * initially:
 * {tailer} -- {header}
 * <p>
 * addFirst(1)
 * {tailer} -- {1} -- {header}
 * <p>
 * addFirst(2)
 * {tailer} -- {1} -- {2} -- {header}
 * <p>
 * addLast(4)
 * {tailer} -- {4} -- {1} -- {2} -- {header}
 * <p>
 * {1}.next = {2}
 * {1}.prev = {4}
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> header;
    private Node<Item> tailer;
    private int size;

    // construct an empty deque
    public Deque() {
        header = new Node<>(null);
        tailer = new Node<>(null);

        header.prev = tailer;
        tailer.next = header;

        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null item");
        }
        Node<Item> newest = new Node<>(item);
        Node<Item> first = header.prev;

        first.next = newest;
        newest.prev = first;

        newest.next = header;
        header.prev = newest;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null item");
        }

        Node<Item> newest = new Node<>(item);
        Node<Item> last = tailer.next;

        last.prev = newest;
        newest.next = last;

        tailer.next = newest;
        newest.prev = tailer;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        Node<Item> first = header.prev;
        header.prev = first.prev;
        first.prev.next = header;

        first.prev = null;
        first.next = null;
        size--;
        return first.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        Node<Item> last = tailer.next;
        tailer.next = last.next;
        last.next.prev = tailer;

        last.next = null;
        last.prev = null;
        size--;
        return last.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();

    }

    private class DequeIterator implements Iterator<Item> {

        // The current node in the iteration, starting from the front (header.prev)
        Node<Item> currentNode;

        // Constructor for the iterator, initializes currentNode to the first item (front)
        public DequeIterator() {
            currentNode = header.prev;
        }

        // Checks if there are more items to iterate over
        // Returns true if currentNode is not the tailer (sentinel node)
        @Override
        public boolean hasNext() {
            return currentNode != tailer;
        }

        // Returns the next item in the iteration and advances the iterator
        // Throws NoSuchElementException if no more items
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in the deque");
            }
            Item nextItem = currentNode.item;
            currentNode = currentNode.prev;  // Move to the previous node (towards the back)
            return nextItem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // Test the Deque constructor
        Deque<String> deque = new Deque<>();
        StdOut.println("Deque created. Is empty: " + deque.isEmpty());
        StdOut.println("Size: " + deque.size());

        // Test addFirst
        deque.addFirst("first");
        StdOut.println("Added 'first' to front. Is empty: " + deque.isEmpty());
        StdOut.println("Size: " + deque.size());

        // Test addLast
        deque.addLast("last");
        StdOut.println("Added 'last' to back. Size: " + deque.size());

        // Test addFirst again
        deque.addFirst("second");
        StdOut.println("Added 'second' to front. Size: " + deque.size());


        // Test iterator
        StdOut.println("Iterating from front to back:");
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }

        // Test removeFirst
        String removed = deque.removeFirst();
        StdOut.println("Removed from front: '" + removed + "'. Size: " + deque.size());

        // Test removeLast
        removed = deque.removeLast();
        StdOut.println("Removed from back: '" + removed + "'. Size: " + deque.size());

        // Test isEmpty and size after removals
        StdOut.println("Is empty: " + deque.isEmpty());
        StdOut.println("Size: " + deque.size());

        // Add more to test further
        deque.addLast("new last");
        deque.addFirst("new first");
        StdOut.println("Added 'new last' and 'new first'. Size: " + deque.size());

        // Final iteration
        StdOut.println("Final iteration:");
        for (String s : deque) {
            StdOut.println(s);
        }
    }

    /**
     * Represents one node in the deque's doubly linked list.
     * Each node stores a deque item, a reference to the next node,
     * and a reference to the previous node.
     *
     * @param <Item> the type of item stored in this node
     */
    private static class Node<Item> {
        /**
         * The item stored in this node.
         */
        Item item;

        /**
         * Reference to the next node toward the back of the deque.
         */
        Node<Item> next;

        /**
         * Reference to the previous node toward the front of the deque.
         */
        Node<Item> prev;

        /**
         * Creates a node containing the specified item.
         *
         * @param item the item to store in this node
         */
        public Node(Item item) {
            this.item = item;
        }
    }
}
