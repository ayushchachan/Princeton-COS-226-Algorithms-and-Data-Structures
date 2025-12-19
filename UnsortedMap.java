import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Ayush Chachan
 */
public class UnsortedMap<X, Y> extends AbstractMap<X, Y> {
    private final ArrayList<Entry<X, Y>> data = new ArrayList<>();       //a list object for storing entries

    /**
     * Return the size of map
     */
    public int size() {
        return data.size();
    }

    /*private utility method*/

    /**
     * Returns the index of key , if key is present in map else return -1
     */
    private int findIndex(X key) {
        Entry<X, Y> e;
        for (int j = 0; j < data.size(); j++) {
            e = data.get(j);
            if (e.getKey().equals(key)) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Return the value associated with key k; returns null if key is not present in map
     */
    public Y get(X k) {
        int i = findIndex(k);
        if (i != -1) {
            return data.get(i).getValue();
        }
        return null;
    }

    /**
     * Inserts a new entry with key k and value v if key is not present then ,
     * else change the value for key k with v and returns the old value
     */
    public Y put(X k, Y v) {
        int i = findIndex(k);
        MapEntry<X, Y> e;
        if (i == -1) {
            e = new MapEntry<>(k, v);
            data.add(e);
            return null;
        } else {
            e = (MapEntry) data.get(i);
            return e.setValue(v);
        }
    }

    /**
     * Removes the corresponding entry if key k is present in map and return
     * the value associated, returns null if key is not present
     */
    public Y delete(X k) {
        int i = findIndex(k);               //index of entry with key k
        if (i != -1) {                      //if k is in map
            Entry<X, Y> lastEntry = data.remove(data.size() - 1);       //last entry in map
            Entry<X, Y> temp = data.set(i, lastEntry);                     //entry to be removed

            return temp.getValue();
        }
        return null;                        //if k is not in map, return null
    }

    /**
     * Returns a iterable collection of all entries in map
     */
    public Iterable<Entry<X, Y>> entrySet() {
        return new EntryCollection();
    }

    //implementation for iterator of entries
    private class EntryCollection implements Iterable<Entry<X, Y>> {
        public Iterator<Entry<X, Y>> iterator() {
            return new EntryIterator();
        }
    }

    private class EntryIterator implements Iterator<Entry<X, Y>> {
        int j = 0;      //maintains a pointer to entries of map, which entry to return

        public boolean hasNext() {
            return j < data.size();
        }

        public Entry<X, Y> next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements to return!");

            return data.get(j++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
