import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

/**
 * @author Ayush Chachan
 */
public class GElement extends GCompound {
    /**
     * creates a new element for a GStack
     * An elements consists of a GRect object with a GLabel object embedded in it
     */
    public GElement(String s, double width, double height) {
        GRect rec = new GRect(width, height);
        GLabel data = new GLabel(s);

        add(rec, -rec.getWidth() / 2.0, -rec.getHeight() / 2.0);
        add(data, -data.getWidth() / 2.0, data.getAscent() / 2.2);
    }

    /**
     * creates a new GElement which will be a GRect of width w and height h
     * and color c
     */
    public GElement(String s, Color c, double width, double height) {
        GRect rec = new GRect(width, height);
        rec.setFilled(true);
        rec.setFillColor(c);
        add(rec, -rec.getWidth() / 2.0, -rec.getHeight() / 2.0);

        GLabel data = new GLabel(s);
        add(data, -data.getWidth() / 2.0, data.getAscent() / 2.2);
    }
}
