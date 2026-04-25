import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;



public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int DEFAULT_CAPACITY = 16;
    private Item[] data;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        data = (Item[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null item");
        }
        data[size] = item;
        size++;
        if (size == data.length) {
            this.resize(2 * data.length);
            
        }
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
            for (int i = 0; i < data.length; i++) {
                newArray[i] = data[i];
            }
            this.data = newArray;
    }


    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item itemToRemove = data[randomIndex];
        size--;
        data[randomIndex] = data[size];
        data[size] = null;

        // resizing the array to 3/4 th if it is less than half
        // if (size < (data.length * 0.25)) {
        //     this.resize((int) Math.ceil(0.5 * data.length));
        // }
        return itemToRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        return data[randomIndex];
    }

    // return an independent iterator over items in random order
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new RandomizedDequeIterator();

    }

    private class RandomizedDequeIterator implements Iterator<Item> {

        // The current node in the iteration, starting from the front (header.prev)
        private int currentIndex;
        private int[] randomPermuation;

        // Constructor for the iterator, initializes currentNode to the first item (front)
        public RandomizedDequeIterator() {
            randomPermuation = StdRandom.permutation(size);
            currentIndex = 0;
        }

        // Checks if there are more items to iterate over
        // Returns true if currentNode is not the tailer (sentinel node)
        @Override
        public boolean hasNext() {
            return currentIndex < randomPermuation.length;
        }

        // Returns the next item in the iteration and advances the iterator
        // Throws NoSuchElementException if no more items
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in the deque");
            }

            return data[randomPermuation[currentIndex++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        StdOut.println("empty: " + queue.isEmpty());
        StdOut.println("size: " + queue.size());

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        StdOut.println("size after enqueue: " + queue.size());
        StdOut.println("sample: " + queue.sample());

        Iterator<Integer> iterator = queue.iterator();
        StdOut.println("iterator has next: " + iterator.hasNext());
        while (iterator.hasNext()) {
            StdOut.println("iterator next: " + iterator.next());
        }
        StdOut.println("iterator has next after loop: " + iterator.hasNext());

        try {
            iterator.next();
        }
        catch (NoSuchElementException e) {
            StdOut.println("iterator next() on exhausted iterator throws: "
                                   + e.getClass().getSimpleName());
        }

        try {
            iterator.remove();
        }
        catch (UnsupportedOperationException e) {
            StdOut.println("iterator remove() throws: " + e.getClass().getSimpleName());
        }

        StdOut.println("dequeue: " + queue.dequeue());
        StdOut.println("empty after removals: " + queue.isEmpty());

        try {
            queue.sample();
        }
        catch (NoSuchElementException e) {
            StdOut.println("sample() on empty queue throws: "
                                   + e.getClass().getSimpleName());
        }

        try {
            queue.dequeue();
        }
        catch (NoSuchElementException e) {
            StdOut.println("dequeue() on empty queue throws: "
                                   + e.getClass().getSimpleName());
        }

        try {
            queue.enqueue(null);
        }
        catch (IllegalArgumentException e) {
            StdOut.println("enqueue(null) throws: " + e.getClass().getSimpleName());
        }
    }
}
