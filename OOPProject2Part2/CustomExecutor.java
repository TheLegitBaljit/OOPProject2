import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.*;

import java.util.concurrent.*;

public class CustomExecutor {
    private PriorityBlockingQueue<Runnable> queue;
    private ThreadPoolExecutor executor;
    private int maxPriority;

    public CustomExecutor() {
        // Set the number of threads to keep in the pool to half the number of processors available for the JVM
        int minimumPoolSize = Runtime.getRuntime().availableProcessors() / 2;

        int maximumPoolSize = Runtime.getRuntime().availableProcessors() - 1;

        long AliveTime = 300;

        queue = new PriorityBlockingQueue<>(11, (r1, r2) -> {
            Task t1 = (Task) r1;
            Task t2 = (Task) r2;
            return Integer.compare(t1.prio, t2.prio);
        });
        executor = new ThreadPoolExecutor(minimumPoolSize, maximumPoolSize, AliveTime, TimeUnit.MILLISECONDS, queue);

        maxPriority = Integer.MIN_VALUE;
    }

    // Method for submitting a Task instance
    public <T> Future<T> submit(Task<T> task) {
        // Update the maximum priority if necessary
        maxPriority = Math.max(maxPriority, task.prio);

        // Create a FutureTask that wraps the Task
        FutureTask<T> futureTask = new FutureTask<>(task);

        this.executor.submit(futureTask);
        // Submit the FutureTask to the ThreadPoolExecutor
        return futureTask ;
    }

    // Method for creating a Task instance from a Callable and submitting it
    public <T> Future<T> submit(Callable<T> callable, TaskType type) {

        Task<T> task = Task.createTask(callable,type);

        return this.submit(task);
    }

    // Method for creating a Task instance from a Callable and submitting it
    public <T> Future<T> submit(Callable<T> callable) {

        Task<T> task = Task.createTask(callable);

        // Submit the FutureTask to the ThreadPoolExecutor
        return this.submit(task);
    }

    //Method for terminating the Pool
    public void gracefullyTerminate() {
        executor.shutdown();
    }

    //Method for getting the current max priority
    public int getCurrentMax() {
        return maxPriority;
    }
}