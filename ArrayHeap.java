/**
 * An instance is a priority queue of ints implemented as a min-heap in an
 * array of size 1500. The heap values are themselves the priorities
 */
public class ArrayHeap {

    /**
     * heap invariant for b[0..size-1]:
     * b[0..size-1] is viewed as a min-heap, i.e.
     * 1. Each array element in b[0..size-1] contains a value of the heap.
     * 2. The children of each b[k] are b[2k+1] and b[2k+2].
     * 3. The parent of each b[k] is b[(k-1)/2].
     * 4. The priority of the parent of each b[k] is <= the priority of b[k].
     * 5. Priorities for the b[k] used for the comparison in point 4
     * are the elements themselves: the priority of b[k] is b[k].
     */
    private final int[] b = new int[1500];
    private int size; // number of elements in the priority queue (and heap)

    /**
     * Constructor: an empty heap.
     */
    public ArrayHeap() {
    }

    /**
     * Return a string that gives this priority queue, in the format:
     * [item0, item1, ..., item(n-1)]
     * That is, the list is delimited by '['  and ']' and ", " (i.e. a
     * comma and a space char) separate adjacent items.
     */
    public String toString() {
        String s = "[";
        for (int k = 0; k < size; k = k + 1) {
            if (s.length() > 1) {
                s = s + ", ";
            }
            s = s + b[k];
        }
        return s + "]";
    }

    /**
     * Return the number of elements in the priority queue.
     * This operation takes constant time.
     */
    public int size() {
        return size;
    }

    /**
     * Add e with priority e to the priority queue.
     * The time is O(log heap-size).
     * Precondition: the size of the heap is < 1500.
     */
    public void add(int e) {
        b[size] = e;
        size = size + 1;
        bubbleUp(size - 1);
    }

    /**
     * Return the element of the priority queue with lowest priority, without
     * changing the queue. This operation takes constant time.
     * Precondition: the priority queue is not empty.
     */
    public int peek() {
        assert 0 < size;
        return b[0];
    }

    /**
     * Remove and return the element of the priority queue with lowest priority.
     * The worst-case time is O(log n).
     * Precondition: the priority queue is not empty.
     */
    public int poll() {
        assert 0 < size;

        if (size == 1) {
            size = 0;
            return b[0];
        }

        // At least 2 elements in queue
        int val = b[0];
        size = size - 1;
        b[0] = b[size];

        bubbleDown(0);
        return val;
    }

    /**
     * Bubble b[k] up in heap to its right place.
     * Precondition: Every b[i] >= its parent except perhaps for b[k]
     */
    private void bubbleUp(int k) {
        // Inv: Every b[i] >= its parent except perhaps b[k]
        while (k > 0) {
            int p = (k - 1) / 2;  //p is k's parent
            if (b[k] >= b[p]) return;

            // Swap b[k] and b[p]
            int bp = b[p];
            b[p] = b[k];
            b[k] = bp;

            k = p;
        }
    }

    /**
     * Bubble b[k] down in heap until it finds the right place.
     * Pre: Every b[i] <= its children except perhaps for b[k].
     */
    private void bubbleDown(int k) {
        // Invariant: Every b[i] <= its children except perhaps for b[k]
        while (2 * k + 1 < size) {
            int c = getSmallerChild(k);
            if (b[k] <= b[c]) return;

            // Swap b[k] and b[c]
            int bc = b[c];
            b[c] = b[k];
            b[k] = bc;

            k = c;
        }
    }

    /**
     * Return the index of the smaller child of b[q]
     * Note: If the child doesn't exist, i.e. 2q+1 >= size, return 21+1.
     */
    private int getSmallerChild(int q) {
        int c = 2 * q + 1;
        if (c + 1 >= size || b[c] < b[c + 1]) return c;
        return c + 1;
    }
}