package concurrency;

import java.util.concurrent.*;
import java.util.*;

class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    public String call() {
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result of TaskWithResult " + id;
    }
}

public class CallableDemoCompletionService {
    static final int NUM_TASKS = 10;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(exec);

        for (int i = 0; i < NUM_TASKS; i++)
            ecs.submit(new TaskWithResult(i));
        try {
            for(int i = 0; i < NUM_TASKS; i++) {
                Future<String> f = ecs.take();
                System.out.println(f.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
