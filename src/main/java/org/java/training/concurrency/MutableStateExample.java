package org.java.training.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MutableStateExample {

    public static void main(String[] args) {

        Lamp lamp = new Lamp();
        lamp.turnOn();

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            while (lamp.isOn()) {

            }
        });

        sleep(1000);
        lamp.turnOff();
        executorService.shutdown();
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
        }
    }

    private static class Lamp {

        private boolean isOn;

        public synchronized void turnOn() {
            isOn = true;
        }

        public synchronized void turnOff() {
            isOn = false;
        }

        public synchronized boolean isOn() {
            return isOn;
        }
    }
}