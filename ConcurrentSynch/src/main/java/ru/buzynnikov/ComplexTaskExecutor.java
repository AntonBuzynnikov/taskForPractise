package ru.buzynnikov;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {

    private final ExecutorService executorService;
    private final CyclicBarrier barrier;

    public ComplexTaskExecutor(int numberOfThreads){
        executorService = Executors.newCachedThreadPool();
        barrier = new CyclicBarrier(numberOfThreads, this::merge);
    }

    public void executeTasks(int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(() -> {
                ComplexTask task = new ComplexTask();
                task.execute();

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.shutdown();
    }


    private void merge(){
        System.out.println("Все задачи выполнены!");
    }
}