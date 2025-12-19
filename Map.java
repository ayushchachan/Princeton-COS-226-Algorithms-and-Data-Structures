/**
 * @author Ayush Chachan
 */
public interface Map<K, V> {
    int size();

    boolean isEmpty();

    V put(K key, V value);

    V get(K key);

    V delete(K key);

    Iterable<K> keySet();

    Iterable<V> values();

    Iterable<Entry<K, V>> entrySet();
}
