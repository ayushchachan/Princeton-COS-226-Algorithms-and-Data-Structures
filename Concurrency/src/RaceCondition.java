public class RaceCondition implements Runnable {

    static int count = 0;

    public void run() {

        for (int i = 0; i < 1000000; i++) {
            count++;
        }
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(new RaceCondition());
        Thread t2 = new Thread(new RaceCondition());

        t1.start();
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count = " + count);
    }
}
