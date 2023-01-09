import java.util.concurrent.Callable;

public class Task<T> implements Callable<T>{
    private Callable<T> operation;
    public int prio;

    public Task(Callable<T> operation,TaskType type)
    {
        this.operation = operation;
        this.prio = type.getPriorityValue();
    }
    public Task(Callable<T> operation)
    {
        this.operation = operation;
        this.prio = 1;
    }

    public static Task createTask(Callable operation,TaskType type)
    {
        return new Task(operation,type);
    }
    public static Task createTask(Callable operation)
    {
        return new Task(operation);
    }

    @Override
    public T call() throws Exception {
        return this.operation.call();
    }

}
