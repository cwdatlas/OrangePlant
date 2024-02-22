import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker implements Runnable{
    final WorkerType type;
    Map<String, TSQueue> queues;
    AtomicBoolean running;

    Worker(WorkerType type, Map<String, TSQueue> queues, AtomicBoolean running){
        this.type = type;
        this.queues = queues;
        this.running = running;

    }
    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while(running.get()) {
            switch (type) {
                case FETCHER: {
                    Orange orange = new Orange();
                    queues.get("fetchedQueue").push(orange);
                    break;
                }
                case PEELER: {
                    Orange orange = queues.get("fetchedQueue").pop();
                    orange.runProcess();
                    queues.get("peeledQueue").push(orange);
                    break;
                }
                case SQUEEZER: {
                    Orange orange = queues.get("peeledQueue").pop();
                    orange.runProcess();
                    queues.get("squeezedQueue").push(orange);
                    break;
                }
                case BOTTLER: {
                    Orange orange = queues.get("squeezedQueue").pop();
                    orange.runProcess();
                    queues.get("bottledQueue").push(orange);
                    break;
                }
                case PROCESSER: {
                    Orange orange = queues.get("bottledQueue").pop();
                    orange.runProcess();
                    queues.get("processedQueue").push(orange);
                    break;
                }
                default:
                    System.out.println("Did not find job for worker");
            }
        }
    }
}
