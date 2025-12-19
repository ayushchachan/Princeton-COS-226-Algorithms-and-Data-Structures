import acm.program.GraphicsProgram;

import java.awt.*;

/**
 * @author Ayush Chachan
 */
public class Generalised_GraphicalTowerOfHanoi extends GraphicsProgram {


    //private instance variables
    private final GStack stack1;    //stack of disks on stack1
    private final GStack stack2;    //stack of disks on stack2
    private final GStack stack3;    //stack of disks on stack3
    private final int n;      //number of disks(stored for class)

    
    /*we will always assume that disks are initially on stack1 and need to be moved to
    stack3 and we have another empty stack2 which we can use for the process*/

    /*Constructor*/
    public Generalised_GraphicalTowerOfHanoi(int num_disks) {
        n = num_disks;      //storing num_disks in an object

        stack1 = new GStack(num_disks);
        stack2 = new GStack(num_disks);
        stack3 = new GStack(num_disks);

        int i = num_disks;
        while (i > 0) {
            double width = 15 * (i + 1);      //width of GElement
            double height = 10;             //height of GElement
            Color c;

            if (i % 2 == 0) {
                c = Color.GREEN;
            } else {
                c = Color.RED;
            }

            GElement e = new GElement("" + i, c, width, height);
            stack1.push(e);
            i--;
        }

        add(stack1, 130, 400);
        add(stack2, 385, 400);
        add(stack3, 640, 400);
    }

    public void run() {


        waitForClick();
        putColorDisksFrom(n, stack1, stack2, stack3);
    }

    /**
     * This method will move n disks from stack a to stack b using another stack c
     */
    public void putDisksFrom(int n, GStack a, GStack b, GStack c) {
        if (a.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
//-----recursive definition-------
        if (n == 1) {
            b.push(a.pop());


        } else {
            putDisksFrom(n - 1, a, c, b);
            b.push(a.pop());

            putDisksFrom(n - 1, c, b, a);
        }
//-----------------end of recursive definition---------*/

    }

    /**
     * This method will move n colored or generalized disks from stack a to stack c using another stack b
     */
    public void putColorDisksFrom(int n, GStack a, GStack c, GStack b) {
        if (n % 2 == 0) {                                     //if n is even
            if (n == 0) {
            } else {
                this.putDisksFrom(n - 1, a, b, c);
                c.push(a.pop());
                this.putDisksFrom(n - 1, b, a, c);
                putColorDisksFrom(n - 2, a, c, b);
            }
        } else {                                            //if n is odd
            if (n == 1) {
            } else {
                this.putDisksFrom(n - 2, a, b, c);
                c.push(a.pop());
                this.putDisksFrom(n - 2, b, a, c);
                putColorDisksFrom(n - 2, a, c, b);
            }
        }
    }


}
