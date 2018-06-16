package org.java.training.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class RaceConditionExample {

    public static void main(String[] args) throws InterruptedException {

        MoneyTransferService moneyTransferService = new MoneyTransferService();
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 500000; i++) {
            startTransferringMoney(moneyTransferService, executorService);
        }
        executorService.shutdown();
    }

    private static void startTransferringMoney(MoneyTransferService moneyTransferService, ExecutorService executorService) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        Account luke = new Account("Luke", 1000);
        Account vader = new Account("Darth Vader", 500);

        executorService.submit(() -> {
            moneyTransferService.transferMoney(luke, vader, 800);
            countDownLatch.countDown();
        });

        executorService.submit(() -> {
            moneyTransferService.transferMoney(vader, luke, 100);
            countDownLatch.countDown();
        });

        executorService.submit(() -> {
            moneyTransferService.transferMoney(luke, vader, 700);
            countDownLatch.countDown();
        });

        executorService.submit(() -> {
            moneyTransferService.transferMoney(vader, luke, 100);
            countDownLatch.countDown();
        });

        countDownLatch.await();
        if (luke.getBalance() <= 0 || vader.getBalance() <= 0) {
            System.out.println("Luke's balance = " + luke.getBalance());
            System.out.println("Darth Vader's balance = " + vader.getBalance());
        }
    }

    private static class MoneyTransferService {
        private static final Random random = new Random(10);

        public synchronized boolean transferMoney(Account from, Account to, int amount) {

            if (isEnoughBalance(from, amount)) {
                simulateDbInteraction();

                simulateDbInteraction();
                return true;
            }
            return false;
        }

        private boolean isEnoughBalance(Account from, int amount) {
            return from.getBalance() - amount >= 0;
        }

        private void simulateDbInteraction() {
            try {
                Thread.sleep(random.nextInt(10));
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static class Account {
        private String name;
        private int balance;

        public Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBalance() {
            return balance;
        }

        public void increaseBalance(int amount) {
            balance += amount;
        }

        public void decreaseBalance(int amount) {
            balance -= amount;
        }
    }
}
