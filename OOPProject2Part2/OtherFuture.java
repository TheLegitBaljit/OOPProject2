import java.util.concurrent.FutureTask;

public class OtherFuture<T> extends FutureTask<T> implements Comparable<OtherFuture<T>> {
    private Task<T> taskCustom;
    private int priority;
    public OtherFuture(Task<T> task) {
        super(task);
        this.taskCustom = task;
        this.priority = task.prio;
    }

    public Task<T> getTask(){
        return taskCustom;
    }
    public int getPriority()
    {
        return this.priority;
    }
    @Override
    public int compareTo(OtherFuture<T> other){
        return Integer.compare(taskCustom.prio,other.getTask().prio);
    }
}