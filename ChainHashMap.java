import java.util.ArrayList;

/**
 * @author Ayush Chachan
 */
public class ChainHashMap<K, V> extends AbstractHashMap<K, V> {
    UnsortedMap<K, V>[] table;


    /**
     * Constructs a new hash table, with given capacity
     */
    public ChainHashMap(int cap) {
        super(cap);
    }

    /**
     * Constructs a new hash table with default capacity
     */
    public ChainHashMap() {
        super();
    }

    /**
     * Creates a new hash table having length equal to given capacity
     */
    protected void createTable() {
        table = (UnsortedMap<K, V>[]) new UnsortedMap[CAPACITY];
        n = 0;
    }

    /*Private utility*/

    /**
     * Resize the table size if the size factor gets more than 0.5
     */
    private void resize(int size) {
        Iterable<Entry<K, V>> entriesSet = this.entrySet();      //entry set before reizing the table
        CAPACITY = size;
        createTable();

        for (Entry<K, V> e : entriesSet) {
            this.put(e.getKey(), e.getValue());
        }
    }

    /**
     * Return the value associated with key; returns null if key is not present in map
     */
    public V get(K key) {


        int i = hashValue(key, 0);       //iniial index of bucket in table where entry might be present

        UnsortedMap<K, V> bucket = table[i];     //another map object linked to ith bucket
        if (bucket == null) return null;
        return bucket.get(key);
    }

    /**
     * Inserts a new entry with key k and value v if key is not present then ,
     * else change the value for key k with v and returns the old value
     */
    public V put(K key, V value) {
        if (value == null) {
            throw new IllegalArgumentException("Invalid type of value!");
        }
        int i = hashValue(key, 0);       //index of bucket in table where entry is(or will be) present

        UnsortedMap<K, V> bucket = table[i];
        if (bucket == null) {
            bucket = table[i] = new UnsortedMap<>();     //another map object linked to ith bucket
        }
        int oldSize = bucket.size();
        V answer = bucket.put(key, value);
        n += (bucket.size() - oldSize);     //size may have increased

        if (n / CAPACITY >= 0.5) {
            resize(2 * CAPACITY + 1);
        }
        return answer;

    }

    /**
     * Removes the corresponding entry if key k is present in map and return
     * the value associated, throws an exception if key is not present
     */
    public V delete(K key) {
        int i = hashValue(key, 0);       //index of bucket in table where entry is(or will be) present

        UnsortedMap<K, V> bucket = table[i];     //another map object linked to ith bucket
        if (bucket == null) throw new IllegalArgumentException("Invalid key");
        n--;            //decrease the size of table
        return bucket.delete(key);
    }

    /**
     * return an iterable collection of all entries present in map
     */
    public Iterable<Entry<K, V>> entrySet() {
        ArrayList<Entry<K, V>> buffer = new ArrayList<>();

        for (int i = 0; i < CAPACITY; i++) {
            if (table[i] != null) {
                for (Entry<K, V> e : table[i].entrySet()) {
                    buffer.add(e);
                }
            }
        }
        return buffer;
    }


}
