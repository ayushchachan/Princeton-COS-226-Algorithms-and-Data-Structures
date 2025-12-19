
import acm.graphics.*;
import acm.program.*;

import java.awt.event.MouseEvent;

/**
 * @author Ayush Chachan
 */
public class ShubhamLinkedList extends GraphicsProgram {
    GImage img;

    /**
     * main method
     */
    public static void main(String[] args) {
        new ShubhamLinkedList().start(args);
    }

    /**
     * Initializes the program
     */
    public void init() {
        getGCanvas().addMouseListener(this);
        getGCanvas().addMouseMotionListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        img = new GImage("capture.PNG");
        img.scale(0.3);
        add(img, x - (img.getWidth() / 2.0), y - (img.getHeight() / 2.0));

    }
}
