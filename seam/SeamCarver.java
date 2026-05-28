import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {
    private Picture picture;            // picture instance

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Picture must not be null");
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(this.picture);
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }


    // height of current picture
    public int height() {
        return this.picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x >= this.width() || y >= this.height()) {
            throw new IllegalArgumentException("column and row are outside perscribed range.");
        }
        if (x == 0 || y == 0 || x == (picture.width() - 1) || y == (picture.height() - 1)) {
            return 1000;
        }

        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);
        Color up = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);

        double rX = right.getRed() - left.getRed();
        double gX = right.getGreen() - left.getGreen();
        double bX = right.getBlue() - left.getBlue();

        // calc. (deltaXSquare(x, y)) = (rX(x, y))^2 + (gX(x, y))^2 + (bX(x, y))^2
        double deltaXSquare = Math.pow(rX, 2) + Math.pow(gX, 2) + Math.pow(bX, 2);

        double rY = bottom.getRed() - up.getRed();
        double gY = bottom.getGreen() - up.getGreen();
        double bY = bottom.getBlue() - up.getBlue();


        // calc. (deltaYSquare(x, y)) = (rY(x, y))^2 + (gY(x, y))^2 + (bY(x, y))^2
        double deltaYSquare = Math.pow(rY, 2) + Math.pow(gY, 2) + Math.pow(bY, 2);

        return Math.sqrt(deltaXSquare + deltaYSquare);

    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int W = this.width();
        int H = this.height();

        if (W == 1) {
            return new int[] { 0 };
        }

        double[][] E = getEnergyArray();
        // System.out.println("energy array is = ");
        // printArray(E);
        double[][] minEnergy = new double[H][W];
        int[][] dp = new int[H][W];


        for (int j = 0; j < W; j++) {
            minEnergy[H - 1][j] = E[H - 1][j];
            dp[H - 1][j] = -1;
        }
        // System.out.println("minEnergy = ");
        // printArray(minEnergy);
        // System.out.println("dp = ");
        // printArray(dp);

        for (int i = H - 2; i >= 0; i--) {
            // first fll the 0th index
            if (minEnergy[i + 1][0] < minEnergy[i + 1][1]) {
                minEnergy[i][0] = E[i][0] + minEnergy[i + 1][0];
                dp[i][0] = 0;
            }
            else {
                minEnergy[i][0] = E[i][0] + minEnergy[i + 1][1];
                dp[i][0] = 1;
            }


            for (int j = 1; j < W - 1; j++) {
                int minIndex = j;
                if (minEnergy[i + 1][j - 1] < minEnergy[i + 1][j]) {
                    minIndex = j - 1;
                }
                if (minEnergy[i + 1][j + 1] < minEnergy[i + 1][minIndex]) {
                    minIndex = j + 1;
                }
                dp[i][j] = minIndex;
                minEnergy[i][j] = E[i][j] + minEnergy[i + 1][minIndex];

            }

            // at last fll the (W - 1)th index
            if (minEnergy[i + 1][W - 2] < minEnergy[i + 1][W - 1]) {
                minEnergy[i][W - 1] = E[i][W - 1] + minEnergy[i + 1][W - 2];
                dp[i][W - 1] = W - 2;
            }
            else {
                minEnergy[i][W - 1] = E[i][W - 1] + minEnergy[i + 1][W - 1];
                dp[i][W - 1] = W - 1;
            }

            // System.out.println("minEnergy = ");
            // printArray(minEnergy);
            // System.out.println("dp = ");
            // printArray(dp);
            // System.out.println();
        }

        double minEnergyValue = 1000.0 * H;
        int[] verticalSeam = new int[H];
        for (int j = 0; j < W; j++) {
            if (minEnergy[0][j] < minEnergyValue) {
                minEnergyValue = minEnergy[0][j];
                verticalSeam[0] = j;
            }
        }

        // System.out.println("vertical seam = " + Arrays.toString(verticalSeam));

        for (int i = 1; i < H; i++) {
            verticalSeam[i] = dp[i - 1][verticalSeam[i - 1]];
        }
        // System.out.println("vertical seam = " + Arrays.toString(verticalSeam));
        return verticalSeam;
    }

    public int[] findHorizontalSeam() {
        int W = this.width();
        int H = this.height();

        if (H == 1) {
            return new int[] { 0 };
        }
        this.picture = transpose(this.picture);
        int[] answer = findVerticalSeam();
        this.picture = transpose(this.picture);
        return answer;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam is null");
        }

        int picWidth = width();
        int picHeight = height();

        if (picHeight <= 1) {
            throw new IllegalArgumentException("picHeight must be greater than 1.");
        }

        if (seam.length != picWidth) {
            throw new IllegalArgumentException("seam length must be equal to picWidth.");
        }
        this.picture = transpose(this.picture);
        removeVerticalSeam(seam);
        this.picture = transpose(this.picture);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam is null");
        }

        int picWidth = width();
        int picHeight = height();

        if (picWidth <= 1) {
            throw new IllegalArgumentException("picWidth must be greater than 1.");
        }

        if (seam.length != picHeight) {
            throw new IllegalArgumentException("seam length must be equal to picheight.");
        }

        Picture newPicture = new Picture(width() - 1, height());
        for (int row = 0; row < picHeight; row++) {

            if (seam[row] < 0 || seam[row] >= picWidth) {
                throw new IllegalArgumentException("seam is out of bounds.");
            }
            if (row > 0 && Math.abs(seam[row - 1] - seam[row]) > 1) {
                throw new IllegalArgumentException("seam is out of bounds.");
            }

            int col = 0;
            while (col < seam[row]) {
                newPicture.set(col, row, picture.get(col, row));
                col++;
            }

            col++;
            while (col < picWidth) {
                newPicture.set(col - 1, row, picture.get(col, row));
                col++;
            }
        }
        this.picture = newPicture;
    }

    /**
     * return a transpose picture.
     *
     * @param picture
     * @return
     */
    private Picture transpose(Picture picture) {
        // Dimensions of the given picture
        int picHeight = picture.height();
        int picWidth = picture.width();

        // Transposed picture
        Picture transpose = new Picture(picHeight, picWidth);

        // swap rows and columns
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                transpose.set(row, col, picture.get(col, row));
            }
        }
        return transpose;
    }


    /**
     * give an 2-d array of same size as of picture where each element
     * (i, j) contains energy of pixel (i, j)
     *
     * @return
     */
    private double[][] getEnergyArray() {
        int picHeight = height();
        int picWidth = width();

        double[][] energyArray = new double[picHeight][picWidth];

        for (int i = 0; i < picHeight; i++) {
            for (int j = 0; j < picWidth; j++) {
                energyArray[i][j] = this.energy(j, i);
            }
        }

        return energyArray;
    }

    private void printArray(int[][] A) {
        System.out.println('[');
        for (int i = 0; i < A.length; i++) {
            StdOut.println(Arrays.toString(A[i]) + ",");
        }
        System.out.println(']');
    }

    private void printArray(double[][] A) {
        System.out.println('[');
        for (int i = 0; i < A.length; i++) {
            StdOut.println(Arrays.toString(A[i]) + ",");
        }
        System.out.println(']');
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

}
