import java.util.LinkedList;
import java.util.List;

/**
 * @author Aidan Scott
 * TSQueue, standing for Thread Safe Queue, is a purpose build datastructure to learn more about the complexities of
 * mutual exclution. The queue should never allow more than one thread from accessing it at one single time.
 */
public class TSQueue {
    // Create the datastructures that will actually store the Oranges
    private final List<Orange> queue = new LinkedList<>();

    /**
     * pop() gets an orange from the linkedList
     *
     * @return Orange orange
     */
    public synchronized Orange pop() {
        while (queue.isEmpty()) { // If the queue is empty do not try to pop
            try {
                this.wait();// wait if empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Orange orange = queue.remove(0); // Remove orange from queue
        this.notifyAll(); // Tell all threads that they can stop waiting
        return orange;

    }

    /**
     * push() adds an orange to the linkedlist
     * While there is a thread in any of the synchronized functions, any thread looking to push will have to wait
     * until the lock has been given back to this.
     *
     * @param orange
     */
    public synchronized void push(Orange orange) {
        queue.add(orange); // Add orange to list
        this.notifyAll(); // Threads can stop waiting.
    }

    /**
     * getLength() is a function to pass the functionality of .size() to the
     *
     * @return int length of queue
     */
    public synchronized int getLength() {
        return queue.size();
    }
}
