import java.util.Iterator;

/**
 * @author Ayush Chachan
 */
public abstract class AbstractMap<X, Y> implements Map<X, Y> {

    /*Tells whether the map is empty or not*/
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Return an iterable collection of keys
     */
    public Iterable<X> keySet() {
        return new KeyCollection();
    }
    //------end of entry class---------

    /**
     * Return an iterable collection of values
     */
    public Iterable<Y> values() {
        return new ValueCollection();
    }

    /**
     * return a string representation of map
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[');

        Entry<X, Y> current;                     //current entry to add to s

        Iterator<Entry<X, Y>> iter = this.entrySet().iterator();

        if (!iter.hasNext()) {
            s.append(']');
            return s.toString();
        }

        current = iter.next();

        while (iter.hasNext()) {
            s.append(current.toString() + ", ");
            current = iter.next();
        }

        s.append(']');
        return s.toString();

    }

    //a MapEntry class, provides entry objects to store in map
    protected static class MapEntry<X, Y> implements Entry<X, Y> {
        private X key;
        private Y value;

        /**
         * Constructor, creates a new entry
         */
        public MapEntry(X k, Y v) {
            this.key = k;
            this.value = v;
        }

        //queries method

        /**
         * Return the key in this Entry
         */
        public X getKey() {
            return this.key;
        }

        /**
         * updates the key in the entry
         */
        public void setKey(X k) {
            this.key = k;
        }

        //update methods

        /**
         * Return the value in this entry
         */
        public Y getValue() {
            return this.value;
        }

        /**
         * updates the value of this entry and returns the old value
         */
        public Y setValue(Y v) {
            Y old = this.value;
            this.value = v;
            return old;
        }

        /*Returns a string representation of entry*/
        public String toString() {
            String entry = "<" + key.toString() + ", " + value.toString() + ">";
            return entry;
        }
    }

    //support for KeySet method......
    private class KeyIterator implements Iterator<X> {
        Iterator<Entry<X, Y>> entries = entrySet().iterator();

        /**
         * Returns the next element in the sequence
         */
        public X next() {
            return entries.next().getKey();
        }

        /**
         * Checks whether there are more elements present
         */
        public boolean hasNext() {
            return entries.hasNext();
        }

    }

    private class KeyCollection implements Iterable<X> {
        public Iterator<X> iterator() {
            return new KeyIterator();
        }
    }

    //support for values method
    private class ValueIterator implements Iterator<Y> {
        Iterator<Entry<X, Y>> entries = entrySet().iterator();

        /**
         * Returns the next element in the sequence
         */
        public Y next() {
            return entries.next().getValue();
        }

        /**
         * Checks whether there are more elements present
         */
        public boolean hasNext() {
            return entries.hasNext();
        }
    }

    private class ValueCollection implements Iterable<Y> {

        public Iterator<Y> iterator() {
            return new ValueIterator();
        }
    }
}
    
