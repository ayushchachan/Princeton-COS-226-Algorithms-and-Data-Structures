/**
 * @author Ayush
 */
public abstract class AbstractTree<E> implements Tree<E> {
    public boolean isExternal(Position<E> p) {
        return this.numChildren(p) == 0;
    }

    public boolean isInternal(Position<E> p) {
        return this.numChildren(p) > 0;
    }

    public boolean isRoot(Position<E> p) {
        return p == this.root();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    private int height(Position<E> p) {
        int h = 0;
        for (Position<E> c : this.children(p)) {
            h = Math.max(h, 1 + this.height(c) + 1);
        }
        return h;
    }

}
