/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Arrays;

/**
 * @author Ayush Chachan
 */
public class MultiPerceptron {

    private Perceptron[] all_perceptrons;
    private int n, m;           // m = no. of classes and n = no. of features

    // Creates a multi-perceptron object with m classes and n inputs.
    // It creates an array of m perceptrons, each with n inputs.
    public MultiPerceptron(int m, int n) {
        all_perceptrons = new Perceptron[m];
        this.n = n;
        this.m = m;

        for (int i = 0; i < m; i++) {
            all_perceptrons[i] = new Perceptron(n);
        }
    }

    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {
        MultiPerceptron p = new MultiPerceptron(2, 3);
        double[][] trainingSet = {
                { 3, 4, 5 },
                { 2, 0, -2 },
                { -2, 0, 2 },
                { 5, 4, 3 }
        };

        int[] label_set = { 1, 0, 1, 0 };
        for (int i = 0; i < 4; i++) {
            p.trainMulti(trainingSet[i], label_set[i]);
            System.out.println(Arrays.toString(p.all_perceptrons));
        }
    }

    // Trains this multi-perceptron on the labeled (between 0 and m-1) input.
    public void trainMulti(double[] x, int label) {
        for (int i = 0; i < m; i++) {
            if (i == label) {
                // System.out.println("trained perceptron " + i + " with 1");
                all_perceptrons[i].train(x, 1);
            }
            else {
                // System.out.println("trained perceptron " + i + " with -1");
                all_perceptrons[i].train(x, -1);
            }
        }

    }

    // Returns the number of classes m.
    public int numberOfClasses() {
        return m;
    }

    // Returns the number of inputs n (length of the feature vector).
    public int numberOfInputs() {
        return n;
    }

    // Returns the predicted label (between 0 and m-1) for the given input.
    public int predictMulti(double[] x) {
        double max_weighted_sum = Double.MIN_VALUE;
        int label = 0;

        for (int i = 0; i < m; i++) {
            double weighted_sum = all_perceptrons[i].weightedSum(x);
            if (weighted_sum >= max_weighted_sum) {
                label = i;
                max_weighted_sum = weighted_sum;
            }
        }
        return label;
    }

    // Returns a String representation of this MultiPerceptron, with
    // the string representations of the perceptrons separated by commas
    // and enclosed in parentheses.
    // Example with m = 2 and n = 3: ((2.0, 0.0, -2.0), (3.0, 4.0, 5.0))
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("(");
        for (Perceptron p : this.all_perceptrons) {
            s.append(p.toString() + ", \n");
        }
        s.deleteCharAt(s.length() - 1);
        s.append(")");
        return s.toString();
    }
}
