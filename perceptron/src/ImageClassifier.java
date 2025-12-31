import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Ayush Chachan
 */
public class ImageClassifier {

    // See below.
    public static void main(String[] args) {
        String trainingFile = "digits-training60K.txt";
        String testFile = "digits-testing10K.txt";
        MultiPerceptron myPerceptron = readTrainingFile(trainingFile);

        double errorRate = readTestFile(myPerceptron, testFile);
        System.out.println("error rate = " + errorRate);
        // System.out.println(java.util.Arrays.toString(extractFeatures(pic)));
    }

    public static MultiPerceptron readTrainingFile(String filename) {
        MultiPerceptron perceptron = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));

            int m = Integer.parseInt(br.readLine());        // no of classes

            String[] dimensions = br.readLine().split(" ");

            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            perceptron = new MultiPerceptron(m, width * height);

            String line = br.readLine();
            Grayscale g = new Grayscale();
            while (line != null) {
                // System.out.println("line = " + line);

                String[] lineSplitArray = line.split("\\s+");
                String imgFilename = lineSplitArray[0];
                // System.out.println("trained with " + imgFilename);
                int intLabel = Integer.parseInt(lineSplitArray[1]);

                Picture picOrig = new Picture(imgFilename);


                double[] features = extractFeatures(g.getGrayScale(picOrig));
                perceptron.trainMulti(features, intLabel);
                // System.out.println("perceptron = " + perceptron);
                line = br.readLine();
            }

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        return perceptron;
    }

    public static double readTestFile(MultiPerceptron perceptron, String filename) {
        BufferedReader br = null;
        double errorRate = 1;

        try {

            br = new BufferedReader(new FileReader(filename));

            int m = Integer.parseInt(br.readLine());        // no of classes

            String[] dimensions = br.readLine().split(" ");

            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            String line = br.readLine();

            int total_test = 0;
            double error = 0;

            while (line != null) {
                String[] lineSplitArray = line.split("\\s+");
                String imgFilename = lineSplitArray[0];

                int trueLabel = Integer.parseInt(lineSplitArray[1]);
                Picture picOrig = new Picture(imgFilename);
                Grayscale g = new Grayscale();

                double[] features = extractFeatures(g.getGrayScale(picOrig));

                int predictLabel = perceptron.predictMulti(features);

                System.out.println("trueLabel = " + trueLabel);
                System.out.println("image = " + imgFilename);
                System.out.println("predictLabel = " + predictLabel);

                if (trueLabel != predictLabel) {
                    error += 1;
                }
                total_test += 1;
                line = br.readLine();
            }
            errorRate = error / total_test;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        return errorRate;
    }

    // Creates a feature vector (1D array) from the given picture.
    public static double[] extractFeatures(Picture p1) {
        double[] vector = new double[p1.height() * p1.width()];
        for (int i = 0; i < p1.height(); i++) {
            for (int j = 0; j < p1.width(); j++) {

                int pixValue = p1.get(j, i).getRed();          // since image is grayscale
                vector[i * p1.width() + j] = pixValue;
            }
        }
        return vector;
    }

    private static void printPictureAsArray(Picture p1) {
        String[][] pixelArray = new String[p1.height()][p1.width()];

        for (int i = 0; i < p1.width(); i++) {
            for (int j = 0; j < p1.height(); j++) {
                Color c = p1.get(j, i);
                pixelArray[i][j] = "(" + c.getRed() + ", " + c.getGreen() + ", "
                        + c.getBlue() + ")";
            }
        }
        for (String[] A : pixelArray) {
            System.out.println(java.util.Arrays.toString(A));
        }
    }
}
