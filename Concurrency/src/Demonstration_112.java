class ThreadId implements Runnable {
    public void run() {
        try {
            // Display the thread that is running
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
        } catch (Exception ex) {
            // Throwing an Exception
            ex.printStackTrace();
        }
    }
}

public class Demonstration_112 {
    public static void main(String[] args) {
        int n = 8;              // number of threads
        for (int i = 0; i < n; i++) {
            (new Thread(new ThreadId())).start();
        }

    }
}


