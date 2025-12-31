/**
 * @author Ayush Chachan
 */
public class Grayscale {

    public Picture getGrayScale(Picture p1) {

        Picture p2 = new Picture(p1.width(), p1.height());
        for (int i = 0; i < p1.height(); i++) {
            for (int j = 0; j < p1.width(); j++) {

                p2.set(j, i, Luminance.toGray(p1.get(j, i)));
            }
        }

        return p2;
    }
}
