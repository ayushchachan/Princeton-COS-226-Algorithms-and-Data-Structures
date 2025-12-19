import acm.graphics.GCompound;
import acm.graphics.GRect;

import java.awt.*;
import java.util.Stack;

/**
 * @author Ayush Chachan
 */
public class GStack extends GCompound {
    //variables associated with base
    private static final int BASE_HEIGHT = 6;
    //variables associated with rod
    private static final int ROD_WIDTH = 3;
    /*private instance variables*/
    private final GRect base;
    private final GRect rod;
    private final int BASE_WIDTH;
    private final int ROD_HEIGHT;
    //x and y location(to keep track for where the next element will be added
    //to stack
    private final double elementX;
    Stack<GElement> elements;
    private double elementY;

    /**
     * Constructs a new GStack object which can contain at most n elements
     */
    public GStack(int n) {
        //defining base and rod dimensions a/c n
        BASE_WIDTH = 15 * (n + 5);
        ROD_HEIGHT = 20 * n;

        //initializing the fields
        base = new GRect(BASE_WIDTH, BASE_HEIGHT);
        base.setFilled(true);
        base.setFillColor(Color.GRAY);

        rod = new GRect(ROD_WIDTH, ROD_HEIGHT);
        rod.setFilled(true);
        rod.setFillColor(Color.GRAY);
        elements = new Stack<>();

        add(base, -base.getWidth() / 2.0, -base.getHeight());
        add(rod, -rod.getWidth() / 2.0, -rod.getHeight() - base.getHeight());

        //updating the elementX and elementY
        elementX = 0;
        elementY = -base.getHeight();
    }

    /**
     * adds a new GElement to the stack
     */
    public void push(GElement e) {
        //System.out.println("-----this statement is executed------");
        //remove(rod);
        remove(this.rod);
        elements.push(e);

        //x and y were next GElement will be added
        double x = 0;
        double y = elementY - e.getHeight() / 2.0;

        add(e, x, y);
        elementY -= e.getHeight();

        pause(100);     //pause for 0.5 seconds after every push

    }

    /**
     * removes the topmost element from stack
     */
    public GElement pop() {
        GElement e = elements.pop();
        elementY += e.getHeight();
        remove(e);

        pause(500);     //pause for 0.5 seconds after every pop
        return e;
    }

    //overloaded push() method
    public void push(int i) {

        double width = 10 * (i + 1);      //width of GElement
        double height = 20;             //height of GElement


        GElement e = new GElement("" + i, width, height);
        this.push(e);

    }

    /**
     * return, but does not remove, the topmost element of stack
     */
    public GElement peek() {
        return elements.peek();
    }

    /*tells whether the stack is empty or not*/
    public boolean isEmpty() {
        return elements.isEmpty();
    }

}
