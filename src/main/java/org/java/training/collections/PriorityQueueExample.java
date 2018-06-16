package org.java.training.collections;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityQueueExample {

    public static void main(String[] args) {
        Queue<PriorityItem> queue = createPriorityQueue();

        queue.add(new PriorityItem(Severity.MEDIUM));
        queue.add(new PriorityItem(Severity.MEDIUM));
        queue.add(new PriorityItem(Severity.LOW));
        queue.add(new PriorityItem(Severity.HIGH));
        queue.add(new PriorityItem(Severity.LOW));
        queue.add(new PriorityItem(Severity.LOW));
        queue.add(new PriorityItem(Severity.HIGH));

        System.out.println("Iteration");
        for (PriorityItem priorityItem : queue) {
            System.out.println(priorityItem);
        }

        System.out.println("Polling");
        PriorityItem item;
        while ((item = queue.poll()) != null){
            System.out.println(item);
        }
    }

    /**
     * offer or add - O(log(n))
     * remove element - O(n)
     * poll element - O(log(n))
     * peek element - O(1)
     */
    private static Queue<PriorityItem> createPriorityQueue() {
        return new PriorityQueue<>(Comparator.comparing(PriorityItem::getSeverity));
    }

    private static class PriorityItem {
        private Severity severity;
        private LocalDateTime dateTime;

        public PriorityItem(Severity severity) {
            this.severity = severity;
            this.dateTime = LocalDateTime.now();
        }

        public Severity getSeverity() {
            return severity;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        @Override
        public String toString() {
            return "PriorityItem{" +
                    "severity=" + severity +
                    ", dateTime=" + dateTime +
                    '}';
        }
    }

    private enum Severity {
        LOW,
        MEDIUM,
        HIGH
    }
}
