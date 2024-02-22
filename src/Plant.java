import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Aidan Scott
 * Plant is class that is able to start and create and manage many workers which all do a different task in the
 * Orange processing pipeline
 */
public class Plant implements Runnable {
    // The quantity of oranges that will be per bottle
    private final int ORANGES_PER_BOTTLE = 4;
    // Set the default value of the AtomicBoolean, a boolean object used in concurrent design, it is thread safe and atomic.
    // This allows us to share the data, and when the value is changed in the Plant, it will be changed in the workers.
    private final AtomicBoolean runningWorkers = new AtomicBoolean(true);
    // Map to hold the queues for each task
    private final Map<String, TSQueue> queueMap = new HashMap<>();
    // Array to hold all threads, the worker class does not include methods that must be called, so we only have to keep track of the threads.
    private final Thread[] workers = new Thread[5];

    /**
     * Plant() constructor creates all custom Thread Safe queues (TSQUEUE)
     * A custom thread safe queue was created to learn more about how to effectively build one.
     * All queues are added to the queue map
     * All threads are added to the workers array, so they can be started and stopped later.
     */
    Plant() {
        // adding queues to the dictionary
        queueMap.put("fetchedQueue", new TSQueue());
        queueMap.put("peeledQueue", new TSQueue());
        queueMap.put("squeezedQueue", new TSQueue());
        queueMap.put("bottledQueue", new TSQueue());
        queueMap.put("processedQueue", new TSQueue());

        workers[0] = new Thread(new Worker(WorkerType.FETCHER, queueMap, runningWorkers));
        workers[1] = new Thread(new Worker(WorkerType.PEELER, queueMap, runningWorkers));
        workers[2] = new Thread(new Worker(WorkerType.SQUEEZER, queueMap, runningWorkers));
        workers[3] = new Thread(new Worker(WorkerType.BOTTLER, queueMap, runningWorkers));
        workers[4] = new Thread(new Worker(WorkerType.PROCESSER, queueMap, runningWorkers));

    }


    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     * This specific Run() function starts all the workers.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        for (Thread i : workers) {
            i.start();
        }
    }

    /**
     * StopWorkers() sets runningWorkers to false, which stops all worker's while loops.
     * At this point, they are waited to topped by the join function.
     */
    public void stopWorkers() {
        runningWorkers.set(false); // stops all threads work
        for (Thread i : workers) {
            try {
                i.join(); // what for each thread to stop
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Customary way to handle interruptions
            }
        }
    }

    /**
     * getProcessedOranges() gets the length of the processedQueue which is the number of processed oranges.
     *
     * @return Int Number of processed oranges
     */
    public int getProcessedOranges() {
        return queueMap.get("processedQueue").getLength();
    }

    /**
     * getBottles() calculates and returns the number of Juice bottles that have been made.
     *
     * @return int number of bottles the Plant produced.
     */
    public int getBottles() {
        return getProcessedOranges() / ORANGES_PER_BOTTLE;
    }

    /**
     * getWaste() returns the wasted oranges after processing
     * A Valuable idea could be to add up all unprocessed oranges in all the queues.
     *
     * @return int returns the remainder of oranges after bottling them.
     */
    public int getWaste() {
        return getProcessedOranges() % ORANGES_PER_BOTTLE;
    }
}
