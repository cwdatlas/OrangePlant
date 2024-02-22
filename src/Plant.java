import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Plant implements Runnable {
    private final int ORANGES_PER_BOTTLE = 4;
    private final AtomicBoolean runningWorkers = new AtomicBoolean(true);
    private final Map<String, TSQueue> queueMap = new HashMap<>();
    private final Thread[] workers = new Thread[5];


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
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        for(Thread i:workers){
            i.start();
        }
    }
    public void stopWorkers(){
        runningWorkers.set(false);
        for(Thread i:workers){
            try {
                i.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    //TODO this needs to be thread safe
    //This is being saved to by the plant, and read by the boss
    public int getProcessedOranges() {
        return queueMap.get("processedQueue").getLength();
    }
    //TODO this needs to be thread safe
    //This is being saved to by the plant, and read by the boss
    public int getBottles() {
        return getProcessedOranges() / ORANGES_PER_BOTTLE;
    }
    //TODO this needs to be thread safe
    //This is being saved to by the plant, and read by the boss
    public int getWaste() {
        return getProcessedOranges() % ORANGES_PER_BOTTLE;
    }
}
