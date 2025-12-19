import java.util.Comparator;

/**
 * @author Ayush Chachan
 */
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {
    /**
     * constructs an AVLTree with given comparator
     */
    public AVLTree(Comparator<T> comp) {
        this.comparator = comp;
    }


    /**
     * constructs an AVLTree with default comparator
     */
    public AVLTree() {

    }

    /**
     * insert a new node z to the tree
     */
    public void treeInsert(AVLNode<T> z) {
        if (z == null) return;

        super.treeInsert(z);
        z.updateHeight();
        while (z != null && (isBalanced(z))) {
            z = (AVLNode<T>) z.getParent();
        }
        if (z != null) {

//            System.out.println("unbalanced node z = " + z + ", isBalanced(z) = " + isBalanced(z));
//            System.out.println("z.getLeftChild() = " + z.getLeftChild());
//            System.out.println("z.getRightChild() = " + z.getRightChild());

            this.balanceNode(z);

//            System.out.println("------after balancing------");
//            System.out.println("z.getLeftChild() = " + z.getLeftChild());
//            System.out.println("z.getRightChild() = " + z.getRightChild());
        }
    }

    /**
     * insert a new key k to the tree
     */
    public void treeInsert(T key) {
        AVLNode<T> newest = new AVLNode<>(key, null, null, null);
        this.treeInsert(newest);
    }

    /**
     * deletes the node z from tree
     */
    public void treeDelete(AVLNode<T> z) {
        //System.out.println("AVLTree delete is called with z = " + z + ", left[z] = " + z.getLeftChild() + ", right[z] = " + z.getRightChild());
        super.treeDelete(z);

        /*updating the heigth of the nodes*/
        if (z.hasRightChild()) {
            AVLNode<T> x = (AVLNode<T>) this.treeMinimum(z.getRightChild());
            x.updateHeight();

            while (x != null) {
                if (!this.isBalanced(x)) {
                    this.balanceNode(x);
                    x = (AVLNode<T>) x.getParent();
                }
                x = (AVLNode<T>) x.getParent();
            }
        } else {
            if (z != null) z.updateHeight();
            //System.out.println("height[z] = " + z.getHeight());
            while (z != null) {
                if (!this.isBalanced(z)) {
                    //System.out.println("isBalanced(z) = " + this.isBalanced(z));
                    this.balanceNode(z);
                    z = (AVLNode<T>) z.getParent();
                }
                z = (AVLNode<T>) z.getParent();
            }
        }
    }

    /**
     * finds and delete node with key k
     */
    public void treeDelete(T k) {
        AVLNode<T> n = (AVLNode<T>) this.treeSearch(k);

        if (n != null) this.treeDelete(n);
    }

    /**
     * balances an unbalanced node
     */
    private void balanceNode(AVLNode<T> z) {
        if (!isBalanced(z)) {
            AVLNode<T> y = this.getChildWithMaxHeight(z);
            AVLNode<T> x = this.getChildWithMaxHeight(y);
            this.reStructure(x, y, z);
        }
    }

    /**
     * checks whether the node is balanced or not
     */
    private boolean isBalanced(AVLNode<T> n) {
        int h1 = 0;
        int h2 = 0;

        if (n.hasLeftChild()) {
            h1 = ((AVLNode<T>) n.getLeftChild()).getHeight();
        }

        if (n.hasRightChild()) {
            h2 = ((AVLNode<T>) n.getRightChild()).getHeight();
        }

        return (Math.abs(h1 - h2) <= 1);
    }

    /**
     * returns the child of node x with maximum height
     */
    public AVLNode<T> getChildWithMaxHeight(AVLNode<T> x) {
        if (x == null) {
            throw new IllegalArgumentException("passed null instead of a valid node object");
        }

        AVLNode<T> leftChild = (AVLNode<T>) x.getLeftChild();
        AVLNode<T> rightChild = (AVLNode<T>) x.getRightChild();
        AVLNode<T> answer = leftChild;

        if (answer == null) {
            return rightChild;
        }                                        //if leftchild == null, return rightChild

        if (rightChild != null) {                                                       //if leftChild != null and rightChild != null
            if (rightChild.getHeight() > leftChild.getHeight())
                answer = rightChild;    //if height[r] > height[l] then answer = rightChild
        }
        return answer;
    }

    /**
     * Restructure the tree i.e. balance the tree again by rotation
     * z is imBalanced node, y = child[z] with max. height
     * x = child[y] with max. height
     */
    private void reStructure(AVLNode<T> x, AVLNode<T> y, AVLNode<T> z) {
        if (y == z.getLeftChild()) {
            if (x == y.getLeftChild()) treeRotation(y);
            else {
                treeRotation(x);
                treeRotation(x);
            }
        } else {
            if (x == y.getRightChild()) treeRotation(y);
            else {
                treeRotation(x);
                treeRotation(x);
            }
        }
    }

    private void treeRotation(AVLNode<T> x) {
        if (x.getParent() == null) {
            throw new IllegalArgumentException("Invalid node, node x doesn't have any parent, cannot perform rotation");
        }

        AVLNode<T> y = (AVLNode<T>) x.getParent();

        if (y.getLeftChild() == x) {
            BinarySearchTree<T> rightSubtreeX = this.getSubtreeWithRoot(x.getRightChild());
            if (rightSubtreeX.root != null) {
                rightSubtreeX.root.setParent(y);
            }
            y.setLeftChild(rightSubtreeX.root);
            x.setRightChild(y);
        } else {
            BinarySearchTree<T> leftSubtreeX = this.getSubtreeWithRoot(x.getLeftChild());
            if (leftSubtreeX.root != null) {
                leftSubtreeX.root.setParent(y);
            }
            y.setRightChild(leftSubtreeX.root);
            x.setLeftChild(y);
        }
        AVLNode<T> z = (AVLNode<T>) y.getParent();
        if (z == null) {
            this.root = x;
        } else {
            if (y == z.getLeftChild()) z.setLeftChild(x);
            else z.setRightChild(x);
        }
        x.setParent(z);
        y.setParent(x);

        //updating heights of x, y and z
        x.updateHeight();
        y.updateHeight();
        if (z != null) z.updateHeight();
    }

    protected static class AVLNode<X> extends Node<X> {
        int height;         //a new height field for node

        public AVLNode(X key, Node<X> parent, Node<X> left, Node<X> right) {
            super(key, parent, left, right);

        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int h) {
            this.height = h;
        }

        public void updateHeight() {
            int h = 1;

            if (this.hasLeftChild()) {
                AVLNode<X> left = ((AVLNode<X>) this.getLeftChild());
                h = Math.max(h, 1 + left.getHeight());
            }

            if (this.hasRightChild()) {
                AVLNode<X> right = ((AVLNode<X>) this.getRightChild());
                h = Math.max(h, 1 + right.getHeight());
            }
            this.setHeight(h);
            if (this.getParent() != null) {
                ((AVLNode<X>) this.getParent()).updateHeight();
            }
        }
    }


}
