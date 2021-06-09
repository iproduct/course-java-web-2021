package concurrency;

public class SimpleDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomethingElse();
            }
        }
    }

    void doSomething() {
        System.out.println("Do smthng");
    }

    void doSomethingElse() {
        System.out.println("Do smthng");
    }

    public void test() {
        Thread t1 = new Thread(() -> {
            while (true) {
                rightLeft();
            }
        });
        t1.setName("right-left");
        t1.start();
        Thread t2 = new Thread(() -> {
            while (true) {
                leftRight();
            }
        });
        t2.setName("left-right");
        t2.start();
    }

    public static void main(String[] args) {
        SimpleDeadlock demo = new SimpleDeadlock();
        demo.test();
    }
}
