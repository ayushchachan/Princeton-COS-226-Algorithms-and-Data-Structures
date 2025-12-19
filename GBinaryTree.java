import acm.graphics.*;
import acm.program.*;
import java.util.Random;
import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Ayush Chachan
 */
public class GBinaryTree<E> extends GraphicsProgram {

    HashMap<BinaryTree.Node<E>, GPoint> nodeLocationSet;
    BinaryTree<E> myTree;

    double HORIZONTAL_GAP = 20;
    double VERTICAL_GAP = 50;
    double START_X = 20;
    double TOP_MARGIN = 50;

    /**
     * creates a graphical version of binary tree t
     */
    public GBinaryTree(BinaryTree<E> t) {
        myTree = t;
    }

    /**
     * Default GBinary tree, with animation
     */
    public GBinaryTree() {
        //initializeTree();


    }

    public static void main(String[] args) {
        AVLTree<Integer> t = new AVLTree<>();
        Random rgen = new Random();

        for (int i = 0; i < 30; i++) {
            t.treeInsert(rgen.nextInt(300));
        }
        new GBinaryTree(t).start(args);

    }

    public void init() {
        this.setSize(1300, 500);
        add(new JButton("Pause"), SOUTH);
        nodeLocationSet = new HashMap<>();
        update();
    }

    /**
     * updates the display
     */
    private void update() {
        removeAll();
        //drawGrid();
        drawTree();
    }

    /**
     * draws a grid of vertical and horizontal lines
     */
    private void drawGrid() {
        GLine line;                         //line to add to canvas
        double lineStartX, lineStartY, lineEndX, lineEndY;
        /*drawing vertical lines*/

        lineStartY = TOP_MARGIN - VERTICAL_GAP;
        lineEndY = myTree.height() * VERTICAL_GAP + TOP_MARGIN;
        for (int i = 0; i < myTree.size(); i++) {
            lineStartX = START_X + i * HORIZONTAL_GAP;
            lineEndX = lineStartX;
            line = new GLine(lineStartX, lineStartY, lineEndX, lineEndY);
            add(line);
        }

        /*drawing horizontal lines*/
        lineStartX = 0;
        lineEndX = START_X + myTree.size() * HORIZONTAL_GAP;
        for (int i = 0; i < myTree.height() + 1; i++) {
            lineStartY = TOP_MARGIN + i * VERTICAL_GAP;
            lineEndY = lineStartY;
            line = new GLine(lineStartX, lineStartY, lineEndX, lineEndY);
            add(line);
        }

    }

    /**
     * draws the tree on canvas
     */
    public void drawTree() {

        int i = 0;
        LinkedList<BinaryTree.Node<E>> internalNodeList = new LinkedList();

        for (BinaryTree.Node<E> treeNode : myTree.nodeSet()) {

            double nodeX = START_X + i * HORIZONTAL_GAP;
            double nodeY = TOP_MARGIN + myTree.depth(treeNode) * VERTICAL_GAP;
            drawNode(treeNode, nodeX, nodeY);
            i++;
            this.nodeLocationSet.put(treeNode, new GPoint(nodeX, nodeY));
            if (!treeNode.isExternal()) {
                //System.out.println(treeNode);

                internalNodeList.addLast(treeNode);
            }
        }
        BinaryTree.Node<E> treeNode;
        /**adding branches*/
        while (!internalNodeList.isEmpty()) {
            treeNode = internalNodeList.removeFirst();
            GPoint parentLoc = this.nodeLocationSet.get(treeNode);

            if (treeNode.hasLeftChild()) {
                GPoint leftChildLoc = this.nodeLocationSet.get(treeNode.getLeftChild());
                if (leftChildLoc == null) throw new IllegalArgumentException();
                GLine leftBranch = new GLine(parentLoc.getX(), parentLoc.getY(), leftChildLoc.getX(), leftChildLoc.getY());
                add(leftBranch);
            }

            if (treeNode.hasRightChild()) {
                GPoint rightChildLoc = this.nodeLocationSet.get(treeNode.getRightChild());
                GLine rightBranch = new GLine(parentLoc.getX(), parentLoc.getY(), rightChildLoc.getX(), rightChildLoc.getY());
                add(rightBranch);
            }
        }
    }

    /**
     * draw a node n to x and y
     */
    private void drawNode(BinaryTree.Node<E> n, double x, double y) {
        GLabel nodeLabel = new GLabel(n.getKey().toString());
        nodeLabel.setFont("*-bold-*");
        addLabel(nodeLabel, x, y);
        GOval nodeBoundary = new GOval(30, 30);
        addGOval(nodeBoundary, x, y);

    }

    /**
     * adds a label with its center at x and y
     */
    private void addLabel(GLabel label, double x, double y) {
        double labelX = x - (label.getWidth() / 2);
        double labelY = y + (label.getHeight() * 0.4);
        add(label, labelX, labelY);
    }

    private void addGOval(GOval circle, double x, double y) {
        double ovalX = x - circle.getWidth() / 2;
        double ovalY = y - circle.getHeight() / 2;
        add(circle, ovalX, ovalY);
    }
}
