import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;

/**
 * @author Ayush Chachan An Array based implementation of Min Heap Priority
 * Queue
 */
public class MaxHeap<K, V> {

    //-------end of nested PQEntry class--------
    private final Comparator<K> comp;                         // comparator object to compare entries in Heap
    protected ArrayList<Entry<K, V>> data;   // stores all the entries in an ArrayList

    public MaxHeap(Comparator<K> comp) {
        this.comp = comp;
        this.data = new ArrayList<>();
    }

    public MaxHeap() {
        this(new DefaultComparator<K>());
    }

    protected int compare(Entry<K, V> e1, Entry<K, V> e2) {
        return comp.compare(e2.getKey(), e1.getKey());
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int parent(int i) {
        return (i - 1) / 2;
    }

    public int left(int i) {
        return 2 * i + 1;
    }

    public int right(int i) {
        return 2 * i + 2;
    }

    public boolean hasLeft(int i) {
        int l = left(i);
        return l < size();
    }

    public boolean hasRight(int i) {
        int r = right(i);
        return r < size();
    }

    public int size() {
        return this.data.size();
    }

    protected void heapify_up(int i) {
        while (i > 0) {
            int p = parent(i);
            if (compare(data.get(i), data.get(p)) < 0) {
                this.swap(i, p);
                i = p;
            } else {
                break;
            }
        }
    }

    protected void heapify_down(int i) {
        int smallest = i;

        int l = left(i);
        if (hasLeft(i) && compare(data.get(l), data.get(i)) < 0) {
            smallest = l;
        }

        int r = right(i);
        if (hasRight(i) && compare(data.get(r), data.get(smallest)) < 0) {
            smallest = r;
        }

        if (smallest != i) {
            swap(i, smallest);
            heapify_down(smallest);
        }
    }

    public Entry<K, V> min() {
        if (data.isEmpty()) {
            return null;
        }
        return data.get(0);
    }

    public Entry<K, V> removeMin() {
        if (data.isEmpty()) {
            return null;
        }

        Entry<K, V> answer = data.get(0);

        swap(0, data.size() - 1);
        data.remove(size() - 1);
        heapify_down(0);
        return answer;

    }

    protected void swap(int i, int j) {
        Entry<K, V> e = data.get(i);

        data.set(i, data.get(j));
        data.set(j, e);

        ((PQEntry<K, V>) data.get(i)).setIndex(i);
        ((PQEntry<K, V>) data.get(j)).setIndex(j);
    }

    protected PQEntry<K, V> validate(Entry<K, V> e) throws IllegalArgumentException {
        if (!(e instanceof PQEntry<K, V> pqentry)) {
            throw new IllegalArgumentException("Invalid entry object");
        }

        int j = pqentry.getIndex();

        if (j < 0 || j >= data.size() || data.get(j) != pqentry) {
            throw new IllegalArgumentException("Invalid index of entry");
        }
        return pqentry;
    }

    public Entry<K, V> insert(K key, V val) {
        PQEntry<K, V> newest = new PQEntry<>(key, val, data.size());
        data.add(newest);
        this.heapify_up(data.size() - 1);
        return newest;
    }

    public Entry<K, V> insert(K key) {
        return this.insert(key, null);
    }

    public void remove(Entry<K, V> e) throws IllegalArgumentException {
        PQEntry<K, V> pqentry = validate(e);

        int p = pqentry.getIndex();

        if (p == data.size() - 1) {
            data.remove(data.size() - 1);
        } else {
            this.swap(pqentry.getIndex(), data.size() - 1);
            data.remove(data.size() - 1);
            heapify_up(p);
            heapify_down(p);
        }
    }

    public void replaceKey(Entry<K, V> e, K key) throws IllegalArgumentException {
        PQEntry<K, V> pqentry = validate(e);
        pqentry.setKey(key);

        heapify_down(pqentry.getIndex());
        heapify_up(pqentry.getIndex());
    }

    public void replaceValue(Entry<K, V> e, V value) throws IllegalArgumentException {
        PQEntry<K, V> pqentry = validate(e);
        pqentry.setValue(value);
    }

    public String toString() {
        return data.toString();
    }

    //---------nested PQEntry class----------
    protected static class PQEntry<K, V> implements Entry<K, V> {

        private K key;
        private V value;
        private int index;              // index of the array where this entry is stored

        public PQEntry(K key, V val, int j) {
            this.key = key;
            this.value = val;
            this.index = j;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        public K setKey(K newKey) {
            K old = this.key;
            this.key = newKey;
            return old;
        }

        public int getIndex() {
            return index;
        }

        public int setIndex(int j) {
            int old = index;
            index = j;
            return old;
        }

        public String toString() {
            return "(" + getKey() + ", " + getValue() + ") --> " + index;
        }
    }

}

