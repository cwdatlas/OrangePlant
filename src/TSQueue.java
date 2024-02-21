import java.util.LinkedList;
import java.util.List;

public class TSQueue {
    private final List<Orange> queue = new LinkedList<>();
    private boolean queueLock = false;

    public synchronized Orange pop(){
        while (queueLock || queue.isEmpty()){
            try{
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queueLock = true;
        Orange orange = queue.remove(0);
        queueLock = false;
        this.notifyAll();
        return orange;

    }
    public synchronized boolean push(Orange orange) {
        while (queueLock) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queueLock = true;
        boolean rem = queue.add(orange);
        queueLock = false;
        this.notifyAll();
        return rem;
    }
}
