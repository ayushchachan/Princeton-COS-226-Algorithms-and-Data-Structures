import java.util.Iterator;

/**
 * @author Ayush Chachan
 */
public class LinkedPositionalList<E> implements PositionalList<E> {

    private final Node<E> header;
    //----end of nested node class
    private final Node<E> trailer;
    private int size;

    public LinkedPositionalList() {
        header = new Node<>(null, null, null);
        trailer = new Node<>(null, header, null);
        header.setNext(trailer);
        size = 0;
    }

    /**
     * Validate the Position and return it as a node
     */
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node<E> n)) throw new IllegalArgumentException("Invalid position: not an instance of Node");

        if (n.getNext() == null)
            throw new IllegalArgumentException("Invalid position: p is no longer in the list");
        return n;
    }

    //private utilities

    /**
     * Return the given node as a Position, or null if it is a sentinel
     */
    public Position<E> position(Node<E> node) {
        if (node == header || node == trailer) return null;

        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Position<E> first() {
        return position(header.getNext());
    }

    @Override
    public Position<E> last() {
        return position(trailer.getPrev());
    }

    @Override
    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> n = validate(p);
        return position(n.getPrev());
    }

    @Override
    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> n = validate(p);
        return position(n.getNext());
    }

    /**
     * Adds an element e between given nodes
     */
    private Position<E> addBetween(E elem, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(elem, pred, succ);
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
        return newest;
    }

    //private utilities

    @Override
    public Position<E> addFirst(E e) {
        return addBetween(e, header, header.getNext());
    }

    @Override
    public Position<E> addLast(E e) {
        return addBetween(e, trailer.getPrev(), trailer);
    }

    @Override
    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> n = validate(p);
        return addBetween(e, n.getPrev(), n);
    }

    @Override
    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> n = validate(p);
        return addBetween(e, n, n.getNext());
    }

    @Override
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> n = validate(p);
        E old = n.getElement();
        n.setElement(e);
        return old;
    }

    @Override
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> n = validate(p);
        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());

        n.setNext(null);
        n.setPrev(null);

        size--;
        E elem = n.getElement();
        n.setElement(null);
        return elem;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    public Iterator<Position<E>> positionIterator() {
        return new PositionIterator();
    }

    //-----------nested Node class
    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            this.element = e;
            this.next = n;
            this.prev = p;
        }

        public E getElement() throws IllegalStateException {
            if (next == null)                               //convention of defunct node
                throw new IllegalStateException("Position is invalid");
            return this.element;
        }

        public void setElement(E newE) {
            this.element = newE;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public void setPrev(Node<E> p) {
            this.prev = p;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            this.next = n;
        }

    }

    private class ElementIterator implements Iterator<E> {

        Node<E> current;

        public ElementIterator() {
            current = header.getNext();
        }

        @Override
        public boolean hasNext() {
            return current != trailer;
        }

        @Override
        public E next() {
            E answer = current.getElement();
            current = current.getNext();
            return answer;
        }

    }

    private class PositionIterator implements Iterator<Position<E>> {

        Node<E> current;

        public PositionIterator() {
            current = header.getNext();
        }

        @Override
        public boolean hasNext() {
            return current != trailer;
        }

        @Override
        public Position<E> next() {
            Position<E> answer = current;
            current = current.getNext();
            return answer;
        }

    }

}
