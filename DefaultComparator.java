import java.util.Comparator;

/**
 * @author Ayush Chachan
 */
public class DefaultComparator<E> implements Comparator<E> {

    public int compare(E a, E b) {
        return ((Comparable<E>) a).compareTo(b);
    }
}
