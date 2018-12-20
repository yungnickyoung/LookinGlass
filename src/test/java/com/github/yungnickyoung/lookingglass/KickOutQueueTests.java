package com.github.yungnickyoung.lookingglass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KickOutQueueTests {
    KickOutQueue<Integer> queue;

    @BeforeEach
    void initAll() {
         queue = new KickOutQueue<>(5);
    }

    @Test
    void offerShouldAddNewElementToNonFullQueueTest() {
        for (int i = 0; i < 5; i++) {
            queue.offer(i);
            assertEquals(Integer.valueOf(0), queue.peek());
            assertEquals(queue.size(), i+1);
        }

        queue.offer(9);
        assertEquals(Integer.valueOf(1), queue.peek());
        assertEquals(queue.size(), 5);
    }

    @Test
    void pollShouldRemoveHeadOfQueueTest() {
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);

        assertEquals(Integer.valueOf(1), queue.poll());
        assertEquals(2, queue.size());

        assertEquals(Integer.valueOf(2), queue.poll());
        assertEquals(1, queue.size());

        assertEquals(Integer.valueOf(3), queue.poll());
        assertEquals(0, queue.size());
       
    }

    @Test
    void offerShouldReturnFalseWhenQueueIsFullTest() {
        for (int i = 0; i < 5; i++) 
            assertEquals(true, queue.offer(i));

        assertEquals(false, queue.offer(6));
        assertEquals(Integer.valueOf(1), queue.peek());

        assertEquals(false, queue.offer(7));
        assertEquals(Integer.valueOf(2), queue.peek());
    }

    @Test
    void pollShouldReturnNullWhenQueueIsEmptyTest() {
        assertEquals(null, queue.poll());

        queue.offer(1);
        queue.poll();

        assertEquals(null, queue.poll());
    }

    @Test
    void peekShouldReturnNullWhenQueueIsEmptyTest() {
        assertEquals(null, queue.peek());

        queue.offer(1);
        queue.poll();

        assertEquals(null, queue.peek());
    }
}