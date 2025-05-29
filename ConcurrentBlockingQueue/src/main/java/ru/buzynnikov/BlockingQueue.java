package ru.buzynnikov;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int maxSize;

    public BlockingQueue(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Размер очереди не может быть меньше 1");
        }
        this.maxSize = maxSize;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        if (this.size() == maxSize) {
            wait();
        }
        queue.add(item);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        if (this.size() == 0) {
            wait();
        }
        T item = queue.remove();
        notifyAll();
        return item;
    }


    public synchronized int size() {
        return queue.size();
    }
}
