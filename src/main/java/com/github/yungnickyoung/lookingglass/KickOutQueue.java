package com.github.yungnickyoung.lookingglass;

import java.util.ArrayList;

/**
 * A very basic queue of fixed size where the head of the queue is removed 
 * ("kicked out") upon the addition of an item to a full queue. Includes basic
 * {@code poll()}, {@code offer()}, and {@code peek()} operations as well as a 
 * {@code peekAt(i)} method for retrieving the element at position {@code i} in the queue. 
 * @param <T> The type of the data held in the queue
 */
public class KickOutQueue<T> {
    private ArrayList<T> queue;
    private final int MAX_SIZE;

    /**
     * Constructs queue with a maximum number of elements
     * @param max_size the maximum number of elements this queue can hold
     */
    public KickOutQueue(int max_size) {
        MAX_SIZE = max_size;
        queue = new ArrayList<>(MAX_SIZE);
    }

    /**
     * Constructs a queue with maximum 10 elements
     */
    public KickOutQueue() {
        this(10);
    }

    /**
     * Adds an element to the back of the queue. If the queue is already full, the
     * head of the queue is first removed before the addition of the new element.
     * @param element The new element to be added 
     * @return {@code true} if the new element was added without any problems; {@code false} if the head
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
     * Retrieves and removes the head of this queue. Returns {@code null} if the queue is empty.
     * @return The head of this queue, or {@code null} if the queue is empty
     */
    public T poll() {
        if (queue.size() > 0) {
            return queue.remove(0);
        } else {
            return null;
        }
    }

    /**
     * Retrieves, but does not remove, the head of this queue. Returns {@code null} if the queue is empty.
     * @return The head of this queue, or {@code null} if the queue is empty
     */
    public T peek() {
        if (queue.size() > 0) {
            return queue.get(0);
        } else {
            return null;
        }
    }

    /**
     * Retrieves, but does not remove, the element at position (index) {@code i} of this queue. The head of the queue
     * is at position 0, the second element is at position 1, etc. Returns {@code null} if the queue is empty.
     * @param i Position of element to retrieve
     * @return The element at position {@code i} or {@code null} if the queue is empty
     * @throws IndexOutOfBoundsException
     */
    public T peekAt(int i) throws IndexOutOfBoundsException {
        if (i >= queue.size() || i < 0) {
            throw new IndexOutOfBoundsException("Attempted KickOutQueue.peekAt() for invalid index" + i);
        }

        if (queue.size() > 0) {
            return queue.get(i);
        } else {
            return null;
        }
    }

    /**
     * Removes all elements from this queue. The queue will be empty after this call returns.
     */
    public void clear() {
        queue.clear();
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