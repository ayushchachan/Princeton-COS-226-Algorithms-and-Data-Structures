/**
 * @author Ayush
 */
public interface Position<E> {
    /**
     * Returns the element stored at this position.
     */
    E getElement() throws IllegalStateException;
}
