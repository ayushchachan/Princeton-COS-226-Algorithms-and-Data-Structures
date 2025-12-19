import acm.graphics.*;
import acm.gui.TableLayout;
import acm.program.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Ayush Chachan
 */
public class NewGraphics extends Program {


    /**
     * Initialize the program
     */
    public void init() {
        setLayout(new TableLayout(2, 3));

        //add(new JTextField("Enter any name"));
        JButton ayush = new JButton("AYUSH");
        ayush.addActionListener(this);


        JButton vipin = new JButton("VIPIN");
        vipin.addActionListener(this);
        add(vipin);

        add(new JLabel("Shubham"));
        add(new JLabel("Mishra"));
        //add(new JButton("rathi"));
        //add(new JButton("shudhish"));

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("AYUSH")) {
            println("2016BB50005");
        } else if (e.getActionCommand().equals("VIPIN")) {
            println("2016CH10118");
        }
    }


}
