import java.util.Map;

public class Worker implements Runnable{
    final String type;
    Map<String, TSQueue> queues;

    Worker(String type, Map<String, TSQueue> queues){
        this.type = type;
        this.queues = queues;
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
        switch(type){
            case "fetcher":{
                Orange orange = new Orange();
                orange.runProcess();
                queues.get("fetchedQueue").push(orange);
            }
            case "peeler":{
                Orange orange = queues.get("fetchedQueue").pop();
                orange.runProcess();
                queues.get("peeledQueue").push(orange);
            }
            case "squeezer":{
                Orange orange = queues.get("peeledQueue").pop();
                orange.runProcess();
                queues.get("squeezedQueue").push(orange);
            }
            case "bottler":{
                Orange orange = queues.get("squeezedQueue").pop();
                orange.runProcess();
                queues.get("bottledQueue").push(orange);
            }
            case "processer":{
                Orange orange = queues.get("bottledQueue").pop();
                orange.runProcess();
                queues.get("processedQueue").push(orange);
            }
        }
    }
}
