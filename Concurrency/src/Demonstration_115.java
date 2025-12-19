/*
Use of yield(), stop() and sleep() methods
 */

class ClassA implements Runnable {
    public void run() {
        System.out.println("Start Thread A ...");
        for (int i = 0; i < 5; i++) {
            if (i == 1) Thread.yield();
            System.out.println("From Thread A, i = " + i);
        }
        System.out.println("... Exit Thread A");
    }
}

class ClassB implements Runnable {
    public void run() {
        System.out.println("Start Thread B ...");
        for (int j = 0; j < 5; j++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Thread B detected interrupt, exiting ...");
                break;
            }
            System.out.println("From Thread B, j = " + j);

            try {
                // Just to slow it down a bit so we can interrupt it
                Thread.sleep(300);

            } catch (InterruptedException ex) {
                System.out.println("Thread B interrupted during sleep, exiting ...");
                break;
            }

            if (j == 2) {
                System.out.println("Thread B deciding to stop itself after j = 2");
                break;
            }
        }
        System.out.println("... Exit Thread B");
    }
}

class ClassC implements Runnable {
    public void run() {
        System.out.println("Start Thread C ....");
        for (int k = 0; k < 5; k++) {
            System.out.println("From Thread C: k = " + k);
            if (k == 3) {
                try {
                    System.out.println("Thread C is goid to sleep for 1 second ...");
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    System.out.println("Thread C interrupted during sleep, exiting...");
                    break;
                }
            }
        }
        System.out.println("... Exit Thread C");
    }
}

public class Demonstration_115 {

    public static void main(String[] args) {
        (new Thread(new ClassA())).start();
        (new Thread(new ClassB())).start();
        (new Thread(new ClassC())).start();

        System.out.println("Main Thread: all three threads have started");

        // Optional: show how interrupt() works from main
        try {
            Thread.sleep(500);  // let Thread B run a bit
        } catch (InterruptedException e) {
            // Not important for this small demo
        }

//        System.out.println("... Main thread: interrupting Thread B");
//        t2.interrupt(); // Ask Thread B to stop if it's still running

        System.out.println("... End of main() execution");

    }
}
