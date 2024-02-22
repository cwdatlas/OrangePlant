import java.util.LinkedList;
import java.util.List;

public class TSQueue {
    private final List<Orange> queue = new LinkedList<>();

    public synchronized Orange pop(){
        while (queue.isEmpty()){
            try{
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Orange orange = queue.remove(0);
        this.notifyAll();
        return orange;

    }
    public synchronized boolean push(Orange orange) {
        boolean rem = queue.add(orange);
        this.notifyAll();
        return rem;
    }

    public synchronized int getLength(){
        return queue.size();
    }
}
