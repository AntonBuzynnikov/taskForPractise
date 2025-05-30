package ru.buzynnikov;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {


    private int factorial;

    public FactorialTask(int factorial) {
        this.factorial = factorial;
    }

    @Override
    protected Long compute() {
        FactorialTask task = new FactorialTask(factorial - 1);
        task.fork();
        if(factorial == 1) return 1L;
        return factorial * task.join();
    }
}
