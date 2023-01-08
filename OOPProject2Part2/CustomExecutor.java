import java.util.Collection;
import java.util.concurrent.*;

public class CustomExecutor {
    private final PriorityBlockingQueue<Runnable> queue;
    private final ThreadPoolExecutor executor;
    private int maxPriority;

    public CustomExecutor() {
        // Set the number of threads to keep in the pool to half the number of processors available for the JVM
        int minimumPoolSize = Runtime.getRuntime().availableProcessors() / 2;

        int maximumPoolSize = Runtime.getRuntime().availableProcessors() - 1;

        long AliveTime = 300;

        queue = new PriorityBlockingQueue<>();

        executor = new ThreadPoolExecutor(minimumPoolSize, maximumPoolSize, AliveTime, TimeUnit.MILLISECONDS, queue);

        maxPriority = Integer.MIN_VALUE;
    }

    // Method for submitting a Task instance
    public <T> Future<T> submit(Task<T> task) {
        // Update the maximum priority if necessary
        maxPriority = Math.max(maxPriority, task.prio);
        return executor.submit(task);
    }

    // Method for creating a Task instance from a Callable and submitting it
    public <T> Future<T> submit(Callable<T> callable, TaskType type) {

        Task<T> task = Task.createTask(callable,type);

        maxPriority = Math.max(maxPriority, task.prio);

        return this.submit(task);
    }

    // Method for creating a Task instance from a Callable and submitting it
    public <T> Future<T> submit(Callable<T> callable) {

        Task<T> task = Task.createTask(callable);

        maxPriority = Math.max(maxPriority, task.prio);

        return this.submit(task);
    }

    // Method for returning the maximum priority in the queue in O(1) time and space complexity
    public int getMaxPriority()
    {
        return this.maxPriority;
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