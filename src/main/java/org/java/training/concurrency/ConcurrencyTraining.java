package org.java.training.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyTraining {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> fetchLogsFromServer(countDownLatch));
        }
        countDownLatch.await();
        System.out.println("All tasks are finished!");

        executorService.shutdown();
    }

    private static void fetchLogsFromServer( CountDownLatch countDownLatch) {
        try {
            Thread.sleep(4000);
            System.out.println("Finished on: "+ Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}
