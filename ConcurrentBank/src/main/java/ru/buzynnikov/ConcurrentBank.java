package ru.buzynnikov;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentBank {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private static Set<BankAccount> accounts = ConcurrentHashMap.newKeySet();

    public BankAccount createAccount(int balance) {
        BankAccount account = new BankAccount(balance, COUNTER.getAndIncrement());
        accounts.add(account);
        return account;
    }

    public void transfer(BankAccount from, BankAccount to, int amount) {
        if(amount < 10) throw new IllegalArgumentException("Перевод должен быть не менее 10 рублей");
        if(from.equals(to)) throw new IllegalArgumentException("Нельзя переводить деньги самому себе");

        BankAccount firstLock, secondLock;
        if(from.getAccountNumber() < to.getAccountNumber()) {
            firstLock = from;
            secondLock = to;
        } else {
            firstLock = to;
            secondLock = from;
        }
        try {
            firstLock.getLock().tryLock(1, TimeUnit.SECONDS);
            secondLock.getLock().tryLock(1, TimeUnit.SECONDS);
            from.withdraw(amount);
            to.deposit(amount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            firstLock.getLock().unlock();
            secondLock.getLock().unlock();
        }
    }

    public int getTotalBalance() {
        int totalBalance = 0;
        for (BankAccount account : accounts) {
            try {
                account.getLock().tryLock(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        for (BankAccount account : accounts) {
            try {
                totalBalance += account.getBalance();
            } finally {
                account.getLock().unlock();
            }
        }

        return accounts.stream().mapToInt(BankAccount::getBalance).sum();
    }

}
