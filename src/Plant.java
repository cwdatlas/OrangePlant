import java.util.HashMap;
import java.util.Map;

public class Plant implements Runnable {
    public final int ORANGES_PER_BOTTLE = 4;
    final Map<String, TSQueue> queueMap = new HashMap<>();
    final TSQueue fetchedQueue = new TSQueue();
    final TSQueue peeledQueue = new TSQueue();
    final TSQueue squeezedQueue = new TSQueue();
    final TSQueue bottledQueue = new TSQueue();
    final TSQueue processedQueue = new TSQueue();
    final Thread[] workers = new Thread[5];
    private Thread fetcher = new Thread(new Worker("fetcher", queueMap));
    private Thread peeler = new Thread(new Worker("peeler", queueMap));
    private Thread squeezer = new Thread(new Worker("squeezer", queueMap));
    private Thread bottler = new Thread(new Worker("bottler", queueMap));
    private Thread processer = new Thread(new Worker("processer", queueMap));
    private int orangesProcessed;

    Plant() {
        // adding queues to the dictionary
        queueMap.put("fetchedQueue", fetchedQueue);
        queueMap.put("peeledQueue", peeledQueue);
        queueMap.put("squeezedQueue", squeezedQueue);
        queueMap.put("bottledQueue", bottledQueue);
        queueMap.put("processedQueue", processedQueue);

        workers[0] = fetcher;
        workers[1] = peeler;
        workers[2] = squeezer;
        workers[3] = bottler;
        workers[4] = processer;

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
        for(Thread i:workers){
            try {
                i.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //TODO this needs to be thread safe
    //This is being saved to by the plant, and read by the boss
    public int getProcessedOranges() {
        return orangesProcessed;
    }
    //TODO this needs to be thread safe
    //This is being saved to by the plant, and read by the boss
    public int getBottles() {
        return orangesProcessed / ORANGES_PER_BOTTLE;
    }
    //TODO this needs to be thread safe
    //This is being saved to by the plant, and read by the boss
    public int getWaste() {
        return orangesProcessed % ORANGES_PER_BOTTLE;
    }
}
