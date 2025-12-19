import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Chankit Chachan
 */
public class HeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {

    protected ArrayList<Entry<K, V>> data;


    public HeapPriorityQueue(Comparator<K> comp) {
        super(comp);
        this.data = new ArrayList<>();
    }

    public HeapPriorityQueue() {
        super();
        this.data = new ArrayList<>();
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

    protected void swap(int i, int j) {
        Entry<K, V> e = this.data.get(i);

        data.set(i, data.get(j));
        data.set(j, e);
    }

    @Override
    public int size() {
        return this.data.size();
    }


    protected void heapify_up(int i) {
        while (i > 0) {
            int p = parent(i);
            if (compare(data.get(i), data.get(p)) < 0) {
                this.swap(i, p);
                i = p;
            } else break;
        }
    }

    protected void heapify_down(int i) {
        int smallest = i;

        int l = left(i);
        if (hasLeft(i) && compare(data.get(l), data.get(i)) < 0) smallest = l;

        int r = right(i);
        if (hasRight(i) && compare(data.get(r), data.get(smallest)) < 0) smallest = r;

        if (smallest != i) {
            swap(i, smallest);
            heapify_down(smallest);
        }
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        Entry<K, V> newest = new PQEntry<>(key, value);
        data.add(newest);
        heapify_up(data.size() - 1);
        return newest;
    }

    @Override
    public Entry<K, V> min() {
        if (data.isEmpty()) return null;
        return data.get(0);
    }

    @Override
    public Entry<K, V> removeMin() {
        if (data.isEmpty()) return null;

        Entry<K, V> answer = data.get(0);

        swap(0, data.size() - 1);
        data.remove(size() - 1);
        heapify_down(0);
        return answer;

    }
}
