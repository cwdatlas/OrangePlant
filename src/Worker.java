import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Aidan Scott
 * Worker will creat a thread that then completes the specific task it was ordered to accomplish.
 */
public class Worker implements Runnable {

    private final WorkerType type; // type set by the Plant class
    private final Map<String, TSQueue> queues; // access to all queues
    private final AtomicBoolean running; // access to switch that turns off workers

    /**
     * Workers() sets values passed to it.
     *
     * @param type
     * @param queues
     * @param running
     */
    Worker(WorkerType type, Map<String, TSQueue> queues, AtomicBoolean running) {
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
     * This specific run method will loop through a switch so a worker can loop through its particular
     * job until it is told to stop which ends the while loop.
     * All all job types are the same, but each one pops and pulls from different queues
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (running.get()) {
            switch (type) {
                case FETCHER: {
                    Orange orange = new Orange(); // Creates an orange, starts with a fetched status
                    queues.get("fetchedQueue").push(orange); // pushes orange to shared TSQueue
                    break;
                }
                case PEELER: {
                    Orange orange = queues.get("fetchedQueue").pop();// pops from shared TSQueue
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
                    // if a worker is misconfigured, this line will be printed to the console.
                    System.out.println("Did not find job for worker");
            }
        }
    }
}
