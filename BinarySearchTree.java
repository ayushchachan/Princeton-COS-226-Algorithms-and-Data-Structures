import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author Vipin Yadav
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
    Comparator<E> comparator;
    Comparator<E> DEFAULT_COMPARATOR = new DefaultComparator();

    public BinarySearchTree(Comparator<E> comp) {
        this.comparator = comp;
    }

    public BinarySearchTree() {
        super();
        this.comparator = new DefaultComparator();
    }

    /**
     * returns the node with key k stored in subtree with root x
     */
    public Node<E> treeSearch(Node<E> x, E k) {
        //System.out.println("----tree search is called() with x = " + x + ", key = " + k);
        if (x != null && k.compareTo(x.getKey()) != 0) {
            //System.out.println("----------------k.compareTo(x.getKey()) != 0------");
            if (k.compareTo(x.getKey()) > 0) {
                return treeSearch(x.getRightChild(), k);
            } else {
                return treeSearch(x.getLeftChild(), k);
            }
        }
        return x;
    }

    /**
     * returns the node with key k stored in current tree
     */
    public Node<E> treeSearch(E k) {
        return this.treeSearch(this.root, k);
    }

    /**
     * insert a new node z to the tree
     */
    public void treeInsert(Node<E> z) {
        //System.out.println("treeInsert(z) , z = " + z);
        if (z == null) return;

        Node<E> x, y = null;
        if (this.root == null) {
            root = z;
        } else {
            x = this.root;
            y = x.getParent();
            E key = z.getKey();

            while (x != null) {
                //System.out.println("x.getKey() = " + x.getKey() + ", key = " + key);
                y = x;

                if (comparator.compare(z.getKey(), x.getKey()) >= 0) {
                    x = x.getRightChild();
                } else {
                    x = x.getLeftChild();
                }
            }

            if (x == null) {
                //System.out.println("comparator = " + comparator);
                if (comparator.compare(z.getKey(), y.getKey()) >= 0) {
                    //System.out.println("----this statement is executed-----");
                    y.setRightChild(z);
                    //System.out.println("y = " + y + ", z = " + z);
                    //System.out.println("y.getRightChild() = " + y.getRightChild());
                } else {
                    y.setLeftChild(z);
                }
                z.setParent(y);
            }
        }
        size++;
        //System.out.println("-----tree insert is called()----- z = " + z);
        //System.out.println("y = " + y);
    }


    /**
     * insert a new key k to the tree
     */
    public void treeInsert(E key) {
        Node<E> newest = new Node<>(key, null, null, null);
        this.treeInsert(newest);
    }

    /**
     * deletes the node z from tree
     */
    public void treeDelete(Node<E> z) {
        //System.out.println("tree_Delete(" + z + ")");
        if (z == null) {
            //throw new IllegalArgumentException("passed null instead of a valid node object, cannot be removed from tree");
            System.out.println("node " + z + " is not present in tree");
            return;
        }
        Node<E> x, y;
        if (z.hasRightChild()) {
            x = this.treeMinimum(z.getRightChild());
            y = x.getParent();
            if (y == z) {
                z.setRightChild(x.getRightChild());
            } else {
                y.setLeftChild(x.getRightChild());
            }
            if (x.hasRightChild()) {
                x.getRightChild().setParent(y);
            }
            z.setKey(x.getKey());
        } else {
            y = z.getParent();
            if (y == null) {
                this.root = z.getLeftChild();
            } else {
                if (y.getLeftChild() == z) {
                    y.setLeftChild(z.getLeftChild());
                } else {
                    y.setRightChild(z.getLeftChild());
                }
            }
            if (z.hasLeftChild()) {
                z.getLeftChild().setParent(y);
            }
        }
        size--;
        //System.out.println("-----after deletion-------");
        //this.printTree();
    }

    /**
     * finds and delete node with key k
     */
    public void treeDelete(E k) {
        Node<E> n = this.treeSearch(k);
        //System.out.println("tree delete() for " + n);
        this.treeDelete(n);
    }

    /**
     * returns the subTree((of main tree)) with root as node x
     */
    protected BinarySearchTree<E> getSubtreeWithRoot(Node<E> x) {

        BinarySearchTree<E> subtree = new BinarySearchTree<>();
        subtree.treeInsert(x);
        return subtree;
    }

    /**
     * returns a new Tree object which is subtree(of main tree) with root as node x
     */
    private BinarySearchTree<E> getNewSubtreeWithRoot(Node<E> x) {
        if (x == null) {
            throw new IllegalArgumentException("passed null instead of a valid node object, cannot return a subtree");
        }
        BinarySearchTree<E> subtree = new BinarySearchTree<>();

        LinkedList<Node<E>> nodesList = new LinkedList<>();
        nodesList.addLast(x);
        while (!nodesList.isEmpty()) {
            Node<E> current = nodesList.removeFirst();
            subtree.treeInsert(current);

            if (current.hasLeftChild()) nodesList.addLast(current.getLeftChild());
            if (current.hasRightChild()) nodesList.addLast(current.getRightChild());
        }
        return subtree;
    }

    /*-----------------------------extra private utility methods, haven't been useful yet---------------------------------------*/

    /**
     * returns the node with minimum key stored in subtree with root x
     */
    public Node<E> treeMinimum(Node<E> x) {
        while (x.hasLeftChild()) {
            x = x.getLeftChild();
        }
        return x;
    }

    /**
     * returns the node with maximum key stored in subtree with root x
     */
    public Node<E> treeMaximum(Node<E> x) {
        while (x.getRightChild() != null) {
            x = x.getRightChild();
        }
        return x;
    }

    /**
     * returns the node with maximum key stored in subtree with root x
     */
    public Node<E> treeMaximum() {
        return treeMaximum(this.root);
    }

    /**
     * returns the node with minimum key stored in subtree with root x
     */
    public Node<E> treeMinimum() {
        return treeMinimum(this.root);
    }

    /**
     * returns the maximum key
     */
    public E getMaximumKey() {
        return this.treeMaximum().getKey();
    }

    /**
     * returns the minimum key
     */
    public E getMinimumKey() {
        return this.treeMinimum().getKey();
    }

    /**
     * returns the successor node i.e. the node with minimum key greater than key[x]
     */
    public Node<E> successor(Node<E> x) {
        if (x.hasRightChild()) {
            return treeMinimum(x.getRightChild());
        }
        Node<E> y = x.getParent();
        while (y != null && x == y.getRightChild()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    private class DefaultComparator implements Comparator<E> {
        public int compare(E a, E b) {
            return a.compareTo(b);
        }
    }


}
