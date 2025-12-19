import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Ayush Chachan
 */
public class Inversion {
    public static void main(String[] args) {

        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("num_inversion.txt"));

            List<Integer> A = new ArrayList<>();


            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                A.add(x);
                //System.out.println(x);
            }

            System.out.println(A.size());

            int[] X = {4, 11, 12, 44, 56, 77, 1, 7, 16, 19, 21, 97};
            ArrayList<Integer> temp = new ArrayList<>();

            for (int x : X) temp.add(x);

            System.out.println("numInversion = " + merge_sort_with_inversion(A).value);

            int[] array = new int[A.size()];

            for (int i = 0; i < A.size(); i++) array[i] = A.get(i);
            System.out.println("count_inversion = " + countInversions(array));

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (scanner != null) scanner.close();
        }

    }


    /**
     * Returns the number of inversion in array A[0.....len(A)-1]
     */
    public static Entry merge_sort_with_inversion(List<Integer> A) {


        if (A.size() <= 1) return new Entry(A, 0);
        int i = 0;
        int j = A.size();

//        System.out.println("i = " + i + ", j = " + j);

        int k = (i + j - 1) / 2;

        List<Integer> left_unsorted = A.subList(i, k + 1);

        List<Integer> right_unsorted = A.subList(k + 1, j);


        Entry left_sorted = merge_sort_with_inversion(left_unsorted);

        Entry right_sorted = merge_sort_with_inversion(right_unsorted);


        Entry merged = merge_with_inversion(left_sorted, right_sorted);

        //System.out.println("merged = " + merged);

        return merged;
    }


    public static Entry merge_with_inversion(Entry left, Entry right) {


        //System.out.println("left = " + left);
        //System.out.println("right = " + right);

        List<Integer> answer = new ArrayList<>();

        List<Integer> l1 = left.key;
        List<Integer> l2 = right.key;

        int i = 0;
        int j = 0;

        long count = 0;

        while (i < l1.size() && j < l2.size()) {
            //System.out.println("left[" + i + "] = " + l1.get(i));
            //System.out.println("left[" + j + "] = " + l2.get(j));

            if (l1.get(i) > l2.get(j)) {
                count = count + l1.size() - i;
                answer.add(l2.get(j));
                j++;
            } else {
                answer.add(l1.get(i));
                i++;
            }
            //System.out.println("count = " + count);
        }

        while (i < l1.size()) {
            answer.add(l1.get(i));
            i++;
        }

        while (j < l2.size()) {
            answer.add(l2.get(j));
            j++;
        }

        //System.out.println("got here too");
        return new Entry(answer, count + left.value + right.value);
    }

    // Complete the countInversions function below.
    static long countInversions(int[] arr) {
        return count_inversion(arr, 0, arr.length - 1);

    }

    public static long count_inversion(int[] A, int p, int r) {
        long num_inversion = 0;
        if (p < r) {
            int q = (p + r) / 2;
            num_inversion = num_inversion + count_inversion(A, p, q);
            num_inversion = num_inversion + count_inversion(A, q + 1, r);
            num_inversion = num_inversion + merge_inversion(A, p, q, r);
        }
        return num_inversion;
    }

    private static long merge_inversion(int[] A, int a, int b, int c) {
        int[] L = Arrays.copyOfRange(A, a, b + 1);
        int[] R = Arrays.copyOfRange(A, b + 1, c + 1);

        int i = 0;
        int j = 0;

        long inversion_count = 0;

        while (i < L.length && j < R.length) {
            if (L[i] <= R[j]) {
                A[a++] = L[i];
                i++;
            } else {
                A[a++] = R[j];

                j++;
                inversion_count = inversion_count + L.length - i;

            }
        }
        if (i == L.length) {
            while (j < R.length) {
                A[a++] = R[j];
                j++;
            }
        } else {
            while (i < L.length) {
                A[a++] = L[i];
                i++;
            }
        }
        return inversion_count;
    }

    static class Entry {
        List<Integer> key;
        long value;

        public Entry(List<Integer> k, long val) {
            this.key = k;
            this.value = val;
        }

        public String toString() {
            return key + ", " + value;
        }

    }

}
