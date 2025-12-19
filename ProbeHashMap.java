import java.util.ArrayList;

/**
 * @author Ayush Chachan
 */
public class ProbeHashMap<X, Y> extends AbstractHashMap<X, Y> {
    MapEntry<X, Y>[] table;     //a table storing entries(key-value pair)

    MapEntry<X, Y> DEFUNCT = new MapEntry<>(null, null);

    /**
     * Constructs a new hash table with given capacity and prime
     */
    public ProbeHashMap(int p, int cap) {
        super(p, cap);
    }

    public ProbeHashMap(int cap) {
        super(cap);
    }      //with default prime

    public ProbeHashMap() {
        super();
    }                //with default prime and capacity

    /**
     * create a new empty table of size capacity
     */
    public void createTable() {
        table = (MapEntry<X, Y>[]) new MapEntry[CAPACITY];
        n = 0;
    }

    /*Private utility*/

    /**
     * Resize the table size if the size factor gets more than 0.5
     */
    private void resize(int size) {
        //System.out.println("resize() is called with size = " + size);
        Iterable<Entry<X, Y>> entriesSet = this.entrySet();      //entry set before reizing the table
        CAPACITY = size;
        createTable();

        for (Entry<X, Y> e : entriesSet) {
            this.put(e.getKey(), e.getValue());
        }
    }


    /**
     * Return the value associated with key k; returns null if key is not present in map
     */
    public Y get(X key) {
        //System.out.println("----get() is called with key = " + key + "----");

        int j = 0;

        int i = hashValue(key, j);         //initial index to look into table


        Entry<X, Y> e;       //entry at ith index of table

        while ((e = table[i]) != null) {

            if (e.getKey().equals(key)) {
                return e.getValue();
            }
            i = hashValue(key, ++j);
            //System.out.println("i = " + i);
        }
        return null;
    }

    /**
     * Inserts a new entry with key k and value v if key is not present then ,
     * else change the value for key k with v and returns the old value
     */
    public Y put(X key, Y value) {
//        System.out.println("----put() is called with key = " + key + ", value = " + value + "----");
//        System.out.println("CAPACITY = " + CAPACITY + ", size = " + this.n);
        int j = 0;
        int i = hashValue(key, j);         //initial index to look into table

        //System.out.println("----hash value for key = " + i + " ----");
        MapEntry<X, Y> e = table[i];       //entry at ith index of table

        while ((e != null) && (e != DEFUNCT)) {  //while each bucket is occupied with another entry object

            if (e.getKey().equals(key)) {                   //if entry with key exist
                Y oldValue = e.getValue();
                e.setValue(value);                          //then change the value
                return oldValue;
            }

            i = hashValue(key, ++j);                         //increment the index or j
            e = table[i];
        }
        this.n++;
        table[i] = new MapEntry<>(key, value);              //if reaches here then table[i] is null or DEFUNCT

        if (((double) n) / CAPACITY >= 0.5) {
            this.resize(2 * CAPACITY + 1);
        }
        return value;
    }

    /**
     * Removes the corresponding entry if key k is present in map and return
     * the value associated, throws an exception if key is not present
     */
    public Y delete(X key) {
        int j = 0;
        int i = hashValue(key, j);         //initial index to look into table


        MapEntry<X, Y> e;       //entry at ith index of table

        while ((e = table[i]) != null) {
            if (e.getKey().equals(key)) {
                Y answer = e.getValue();
                table[i] = DEFUNCT;
                n--;
                return answer;
            }
            i = hashValue(key, ++j);                        //increment the index
        }
        throw new IllegalArgumentException("Invalid key");
    }

    /**
     * return an iterable collection of all entries present in map
     */
    public Iterable<Entry<X, Y>> entrySet() {

        ArrayList<Entry<X, Y>> buffer = new ArrayList<>();

        for (MapEntry<X, Y> e : table) {
            if (e != null && e != DEFUNCT) {
                buffer.add(e);
            }
        }
        return buffer;
    }


}
