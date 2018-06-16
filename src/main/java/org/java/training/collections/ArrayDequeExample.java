package org.java.training.collections;

import java.util.ArrayDeque;

public class ArrayDequeExample {

    public static void main(String[] args) {
        ArrayDeque<Integer> arrayDeque = createArrayDeque();
        arrayDeque.add(101);
        arrayDeque.add(102);
        arrayDeque.add(103);
        arrayDeque.add(104);
        arrayDeque.push(201);
        arrayDeque.offer(202);

        for (int i = 5; i < 20; i++) {
            arrayDeque.add(100 + i);
        }

        for (int i = 3; i < 14; i++) {
            arrayDeque.push(200 + i);
        }

        arrayDeque.push(arrayDeque.peek() + 1000);
        arrayDeque.add(arrayDeque.peekLast() + 1000);

        System.out.println(arrayDeque.size());
        System.out.println(arrayDeque.peek());
        System.out.println(arrayDeque.peekLast());
        System.out.println(arrayDeque.poll());
        System.out.println(arrayDeque.pollLast());

        System.out.println(arrayDeque.size());

        System.out.println(arrayDeque.peek());
        System.out.println(arrayDeque.peek());
        System.out.println(arrayDeque.pollLast());
        System.out.println(arrayDeque.pop());
    }

    /**
     * add/push element - O(1)
     * get first or last element - O(1)
     * poll element - O(1)
     * peek element - O(1)
     */
    private static ArrayDeque<Integer> createArrayDeque() {
        return new ArrayDeque<>();
    }
}
