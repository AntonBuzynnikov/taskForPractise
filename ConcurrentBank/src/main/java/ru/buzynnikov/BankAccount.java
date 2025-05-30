package ru.buzynnikov;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {

    private int balance;
    private final int accountNumber;
    private Lock lock = new ReentrantLock();

    public BankAccount(int balance ,int accountNumber){
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public void deposit(int amount){
        lock.lock();
        try{
            this.balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public int getBalance(){
        lock.lock();
        try {
            return this.balance;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount){
        lock.lock();
        try{
            if (this.balance < amount) throw new RuntimeException("Недостаточно средств на счету!");
            this.balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    public Lock getLock(){
        return this.lock;
    }
    public int getAccountNumber(){
        return this.accountNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount account = (BankAccount) o;
        return accountNumber == account.accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accountNumber);
    }
}
