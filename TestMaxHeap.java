import java.util.Random;

public class TestMaxHeap {

    public static void main(String[] args) {
        MaxHeap<Integer, Integer> pq = new MaxHeap<>();

        Random rgen = new Random();
        for (int i = 0; i < 10; i++) {
            pq.insert(rgen.nextInt(100));
            System.out.println("pq = " + pq);
        }

    }
}
