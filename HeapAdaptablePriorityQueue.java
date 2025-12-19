import java.util.Comparator;
import java.util.Map.Entry;

/**
 * @author Ayush Chachan
 */
public class HeapAdaptablePriorityQueue<K, V> extends HeapPriorityQueue<K, V> implements AdaptablePriorityQueue<K, V> {

    public HeapAdaptablePriorityQueue(Comparator<K> comp) {
        super(comp);
    }
    //-------end of nested AdaptablePQEntry class--------

    public HeapAdaptablePriorityQueue() {
        super();
    }

    protected void swap(int i, int j) {
        super.swap(i, j);
        ((AdaptablePQEntry<K, V>) data.get(i)).setIndex(j);
        ((AdaptablePQEntry<K, V>) data.get(j)).setIndex(i);
    }

    protected AdaptablePQEntry<K, V> validate(Entry<K, V> e) throws IllegalArgumentException {
        if (!(e instanceof AdaptablePQEntry<K, V> pqentry)) throw new IllegalArgumentException("Invalid entry paseed");

        int j = pqentry.getIndex();

        if (j < 0 || j >= data.size() || data.get(j) != pqentry)
            throw new IllegalArgumentException("Invalid index of entry");
        return pqentry;
    }

    public Entry<K, V> insert(K key, V val) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> newest = new AdaptablePQEntry<>(key, val, data.size());
        data.add(newest);
        this.heapify_up(data.size() - 1);
        return newest;
    }

    @Override
    public void remove(Entry<K, V> e) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> pqentry = validate(e);

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

    @Override
    public void replaceKey(Entry<K, V> e, K key) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> pqentry = validate(e);
        pqentry.setKey(key);

        heapify_down(pqentry.getIndex());
        heapify_up(pqentry.getIndex());
    }

    @Override
    public void replaceValue(Entry<K, V> e, V value) throws IllegalArgumentException {
        AdaptablePQEntry<K, V> pqentry = validate(e);
        pqentry.setValue(value);
    }

    public String toString() {
        return data.toString();
    }

    //---------nested AdaptablePQEntry class----------
    protected static class AdaptablePQEntry<K, V> extends PQEntry<K, V> {
        private int index;

        public AdaptablePQEntry(K key, V val, int j) {
            super(key, val);
            this.index = j;
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
