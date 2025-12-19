import java.util.Random;

public class TestMinHeap {

    public static void main(String[] args) {
        MinHeap<Integer, Integer> pq = new MinHeap<>();

        Random rgen = new Random();
        for (int i = 0; i < 10; i++) {
            pq.insert(rgen.nextInt(100));
            System.out.println("pq = " + pq);
        }

    }
}
