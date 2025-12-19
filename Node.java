/**
 * @author Ayush Chachan
 */
public class Node {


    private int value;
    private Node next;


    //constructor1
    public Node(int i) {
        value = i;
    }

    //condtructor2
    public Node(int v, Node n) {
        value = v;
        next = n;

    }

    //return the value stored in node
    public int getValue() {
        return value;
    }

    //changes the value of node
    public void setValue(int i) {
        value = i;
    }

    //returns the next node
    public Node getNextNode() {
        return next;

    }

    //set next node
    public void setNextNode(Node n) {
        next = n;
    }

}
