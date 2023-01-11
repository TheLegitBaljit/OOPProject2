import java.util.ArrayList;
import java.util.concurrent.*;

public class CustomExecutor extends ThreadPoolExecutor {
    private int [] maxPriority ;
    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() - 1,
                300, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>());
        maxPriority = new int [10];
    }
    private <T> Future<T> customSubmit(Task<T> task){
        OtherFuture<T> otherTask = new OtherFuture<>(task);
        maxPriority[task.prio-1]++;
        execute(otherTask);
        return otherTask;
    }
    // Method for submitting a Task instance
    public <T> Future<T> submit(Task<T> task) {

        return customSubmit(task) ;
    }

    // Method for creating a Task instance from a Callable and submitting it
    public <T> Future<T> submit(Callable<T> callable, TaskType type) {

        Task<T> task = Task.createTask(callable,type);

        return customSubmit(task);
    }


    // Method for creating a Task instance from a Callable and submitting it
    public <T> Future<T> submit(Callable<T> callable) {

        Task<T> task = Task.createTask(callable);

        // Submit the FutureTask to the ThreadPoolExecutor
        return customSubmit(task);
    }

    //Method for terminating the Pool
    public void gracefullyTerminate() {
        shutdown();
    }

    //Method for getting the current max priority
    public int getCurrentMax() {
        for (int i = 0; i < maxPriority.length; i++) {
            if(maxPriority[i]>0)
                return i+1;
        }
        return 10;
    }
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        OtherFuture t1 = (OtherFuture) r;
        maxPriority[t1.getPriority() - 1]--;
    }
}

