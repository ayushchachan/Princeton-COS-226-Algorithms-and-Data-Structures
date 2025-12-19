public class HelloRunnable implements Runnable {

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("taskA: " + i);
        }
    }


    public static void main(String[] args) {
        Thread t1 = new Thread(new HelloRunnable());
        t1.start();

    }


}
