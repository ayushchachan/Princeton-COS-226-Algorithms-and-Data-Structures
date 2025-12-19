public class SleepMessages implements Runnable {



    public void run() {
        String[] importantInfo = {
                "Mares eat oats",
                "Does eat oats",
                "Little lamb eat ivy",
                "A kid will eat ivy too"
        };
        int j = 0;
        for (int i = 0; i < importantInfo.length; i++) {
            // pause for 4 sec
            try {
                for (j = 0; j < 1000; j++) {
                    System.out.println(" j = " + j);
                }
                if (Thread.interrupted()) {
                    System.out.println("I have been interrupted.....");
                    throw new InterruptedException();
                }
            } catch (InterruptedException ex) {
                return;
            }

            System.out.println(importantInfo[i]);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SleepMessages());
        t1.start();

        Thread.sleep(7000);
        t1.interrupt();
    }
}
