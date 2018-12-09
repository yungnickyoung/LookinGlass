import java.util.ArrayList;

/**
 * A very basic queue of fixed size where the head of the queue is removed 
 * ("kicked out") upon the addition of an item to a full queue. 
 * @param <T> The type of the data held in the queue
 */
public class KickOutQueue<T> {
    private ArrayList<T> queue;
    private final int MAX_SIZE;

    public KickOutQueue(int max_size) {
        MAX_SIZE = max_size;
        queue = new ArrayList<>(MAX_SIZE);
    }

    /**
     * Adds an element to the back of the queue. If the queue is already full, the
     * head of the queue is first removed before the addition of the new element.
     * @param element The new element to be added 
     * @return True if the new element was added without any problems; false if the head
     * of the queue was first removed before this new addition
     */
    public boolean offer(T element) {
        if (queue.size() < MAX_SIZE) {
            queue.add(element);
            return true;
        } else {
            queue.remove(0);
            queue.add(element);
            return false;
        }
    }

    /**
     * Retrieves and removes the head of this queue. Returns null if the queue is empty.
     * @return The head of this queue, or null if the queue is empty
     */
    public T poll() {
        if (queue.size() > 0) {
            T removedElement = queue.get(0);
            queue.remove(0);
            return removedElement;
        } else {
            return null;
        }
    }

    /**
     * Retrieves, but does not remove, the head of this queue. Returns null if the queue is empty.
     * @return The head of this queue, or null if the queue is empty
     */
    public T peek() {
        if (queue.size() > 0) {
            return queue.get(0);
        } else {
            return null;
        }
    }

    /**
     * @return number of elements in the queue
     */
    public int size() {
        return queue.size();
    }

    /**
     * @return the maximum number of elements this queue can hold before kicking out
     *  the oldest element
     */
    public int maxSize() {
        return MAX_SIZE;
    }


}