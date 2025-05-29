package ru.buzynnikov;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new BlockingQueue<>(10);

        Runnable producer = () -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    queue.enqueue(ThreadLocalRandom.current().nextInt());
                    System.out.println("Добавлен элемент: " + queue.size());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable consumer = () -> {
            try {
                while (true) {
                    Thread.sleep(500);
                    queue.dequeue();
                    System.out.println("Удалён элемент: " + queue.size());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable producer2 = () -> {
            try {
                while (true) {
                    Thread.sleep(700);
                    queue.enqueue(ThreadLocalRandom.current().nextInt());
                    System.out.println("Добавлен элемент: " + queue.size());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        try(ExecutorService executor = Executors.newFixedThreadPool(10)){
            executor.submit(producer);
            executor.submit(producer2);
            executor.submit(consumer);
        }

    }
}