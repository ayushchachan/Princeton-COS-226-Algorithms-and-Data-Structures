class ThreadA extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("From Thread A with i = " + i);
        }
        System.out.println("Exiting from Thread A...");
    }
}

class ThreadB extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("From Thread B with i = " + i);
        }
        System.out.println("Exiting from Thread B...");
    }
}

class ThreadC extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("From Thread C with i = " + i);
        }
        System.out.println("Exiting from Thread C...");
    }
}

public class Demonstration_111 {
    public static void main(String[] args) {
        ThreadA a = new ThreadA();
        ThreadB b = new ThreadB();
        ThreadC c = new ThreadC();
        a.start();
        b.start();
        c.start();
        System.out.println("------Main Thread exited------");

    }
}
