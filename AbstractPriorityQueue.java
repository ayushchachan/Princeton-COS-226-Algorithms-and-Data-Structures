import java.util.Comparator;
import java.util.Map.Entry;

/**
 * @author Chankit Chachan
 */
public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {

    private final Comparator<K> comp;
    //---------nested PQEntry class-----------
    protected static class PQEntry<K, V> implements Entry<K, V> {

        private K key;
        private V value;

        public PQEntry(K k, V val) {
            this.key = k;
            this.value = val;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
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
    }

    //------------end of nested PQEntry class---------------

    protected AbstractPriorityQueue(Comparator<K> c) {
        this.comp = c;
    }

    protected AbstractPriorityQueue() {
        this(new DefaultComparator<K>());
    }

    protected int compare(Entry<K, V> e1, Entry<K, V> e2) {
        return comp.compare(e1.getKey(), e2.getKey());
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
