import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Ayush Chachan
 */
public class BinaryTree<E> implements Iterable<E> {
    protected Node<E> root;
    //---end of Nested node class----
    protected int size;

    /**
     * returns a reference to the root node
     */
    public Node<E> root() {
        return this.root;
    }

    /**
     * returns the size of tree
     */
    protected int size() {
        return this.size;
    }

    protected boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * returns the height of node n
     * height of a node is the length of longest path from node to a leaf
     */
    protected int height(Node<E> n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.getLeftChild()), height(n.getRightChild()));
    }

    /**
     * returns the depth of node n
     */
    public int depth(Node<E> n) {
        if (n == null)
            throw new IllegalArgumentException("Null passed instead of a valid node object, cannote compute depth");
        int d = 0;
        while (n != this.root) {
            n = n.getParent();
            d++;
        }
        return d;
    }

    /**
     * return an iterator for elements stored in tree
     */
    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    /**
     * return an iterable collection of all nodes in tree
     */
    public Iterable<Node<E>> nodeSet() {
        return new NodesIterable();
    }

    /**
     * returns the height of the tree
     */
    public int height() {
        return this.height(this.root);
    }

    public Node<E> left(Node<E> n) {return n.getLeftChild();}

    public Node<E> right(Node<E> n) {return n.getRightChild();}


    //extra method for printing binary search tree
    protected void printTree() {
        System.out.println("-----------printing BST using BFS-------");
        //System.out.println("root = " + this.root);

        LinkedList<Node<E>> nodeList = new LinkedList<>();
        nodeList.add(this.root);
        int i = 1;
        while (!nodeList.isEmpty()) {
            Node<E> current = nodeList.removeFirst();

            if (current.hasLeftChild()) nodeList.add(current.getLeftChild());

            if (current.hasRightChild()) nodeList.add(current.getRightChild());

            System.out.print(current.getKey() + ", ");
        }
        System.out.println();

    }

    /**
     * returns a string representation of tree
     */
    public String toString() {
        return super.toString();
    }

    //-----Nested Node Class-------
    protected static class Node<X> {
        private X key;       //key(data) stored in node object
        private Node<X> p;             //pointer to the parent node
        private Node<X> l;          //pointer to the left child
        private Node<X> r;       //pointer to the right child

        /**
         * Constructs a new node object for tree
         */
        public Node(X data, Node<X> parent, Node<X> left, Node<X> right) {
            this.key = data;
            this.p = parent;
            this.l = left;
            this.r = right;
        }

        //overloaded constructor

        /**
         * constructs a new node with parent, left and right all set to null
         */
        public Node(X data) {
            this(data, null, null, null);
        }

        /**
         * No argument constructor
         * return a node with all fields set to null
         */
        public Node() {
            this(null, null, null, null);
        }

        /**
         * returns true if node has left child
         */
        public boolean hasLeftChild() {
            return this.l != null;
        }

        /**
         * returns true if node n has right sibling
         */
        public boolean hasRightChild() {
            return this.r != null;
        }

        //query methods
        public Node<X> getParent() {
            return this.p;
        }

        //update methods
        public void setParent(Node<X> parent) {
            this.p = parent;
        }

        public Node<X> getLeftChild() {
            return this.l;
        }

        public void setLeftChild(Node<X> left) {
            this.l = left;
        }

        public Node<X> getRightChild() {
            return this.r;
        }

        public void setRightChild(Node<X> right) {
            this.r = right;
        }

        public X getKey() {
            return this.key;
        }

        public void setKey(X k) {
            this.key = k;
        }

        /**
         * returns true if node is a leaf i.e both children are null
         */
        public boolean isExternal() {
            return (!this.hasLeftChild()) && (!this.hasRightChild());
        }

        /**
         * return the string representation of node
         */
        public String toString() {
            return ("Node<" + this.key.getClass() + "> : " + this.key);
        }
    }

    private class NodesIterable implements Iterable<Node<E>> {
        public Iterator<Node<E>> iterator() {
            return new NodeIterator();
        }
    }

    /*support for iterator*/
    private class ElementIterator implements Iterator<E> {
        NodeIterator iter = new NodeIterator();

        public boolean hasNext() {
            return iter.hasNext();
        }

        public E next() {
            return iter.next().getKey();
        }
    }

    private class NodeIterator implements Iterator<Node<E>> {
        Node<E> current;

        /**
         * Constructs a new NodeIterator object and initialize current to an appropriate node
         */
        public NodeIterator() {
            this.current = root();
            Node<E> x = current.getLeftChild();
            while (x != null) {
                current = x;
                x = x.getLeftChild();
            }
        }

        public boolean hasNext() {
            return current != null;
        }

        public Node<E> next() {
            Node<E> answer = current;

            if (current.hasRightChild()) {
                current = current.getRightChild();

                while (current.hasLeftChild()) {
                    current = current.getLeftChild();
                }
            } else {
                Node<E> y = current.getParent();
                if (y == null) current = null;
                else if (current == y.getLeftChild()) {
                    current = current.getParent();
                } else {
                    while (y != null && y.getRightChild() == current) {
                        current = y;
                        y = y.getParent();

                    }
                    current = y;
                }
            }
            return answer;
        }
    }
}
