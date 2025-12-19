// import java.util.Random;

/**
 * @author Ayush Chachan
 */
public abstract class AbstractHashMap<A, B> extends AbstractMap<A, B> {
    protected int CAPACITY;     //capacity i.e. max no of elements that can be stored
    protected int n;            //size of map
    // private int scale, shift;
    // private int prime;

    /*constructors*/

    /**
     * Constructs an empty map with prime as p, and CAPACITY as cap
     * and random value for scale and shift parameter
     */
    public AbstractHashMap(int p, int cap) {
        CAPACITY = cap;
        // prime = p;
        // Random rgen = new Random();     //a random number generator
        // scale = rgen.nextInt(prime) + 1;
        // shift = rgen.nextInt(prime);
        createTable();
    }

    /**
     * Constructor with default prime value and CAPACITY as cap
     */
    public AbstractHashMap(int cap) {
        this(109345121, cap);
    }

    /*Default Constructor, with default prime and default capacity*/
    public AbstractHashMap() {
        this(13);
    }

    /**
     * Returns the size of hash table i.e. total number of entries present in table
     */
    public int size() {
        return n;
    }

    /**
     * A hash function
     */
    protected int hashFunc1(A k) {
        int hc = k.hashCode();      //hash code for key k
        //System.out.println("For " + k.toString() + ", hash code: " + hc);
        //return ((hc % CAPACITY) + CAPACITY)%CAPACITY;
        //return (Math.abs(hc) % CAPACITY);
        return Math.abs(hc) % CAPACITY;
        //return (Math.abs((scale*hc + shift) % prime) %CAPACITY);     //hash value
    }

    /**
     * 2nd hash function
     */
    protected int hashFunc2(A k) {
        int hc = Math.abs(k.hashCode());
        return 1 + (hc % (CAPACITY - 2));
    }

    /**
     * Computes the hash value for key k, using double hashing
     */
    protected int hashValue(A k, int i) {
        return (hashFunc1(k) + i * i) % CAPACITY;
    }

    /*Abstract methods, need to declared in subclasses*/
    protected abstract void createTable();

}
