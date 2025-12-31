/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.*;

/**
 * @author Ayush Chachan
 */
public class Luminance {

    public static double intensity(Color c) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        return 0.299 * r + 0.587 * g + 0.114 * b;
    }

    public static Color toGray(Color c) {
        int lum = (int) Math.round(intensity(c));
        Color gray = new Color(lum, lum, lum);
        return gray;
    }
}
