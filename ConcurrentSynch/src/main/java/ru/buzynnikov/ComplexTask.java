package ru.buzynnikov;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ComplexTask{

    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;

    public ComplexTask() {
        this.id = count.incrementAndGet();
    }

    public void execute(){
        System.out.println(Thread.currentThread().getName() + " начал работу с задачей " + this.id);
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(4000, 8000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " закончил работу с задачей " + this.id);
    }
}