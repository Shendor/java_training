package org.java.training.collections;

import java.util.*;

public class ListExample {

    public static void main(String[] args) {
        List<String> list = createArrayList();

        list.add("sample1");
        list.add("sample3");
        list.add("sample4");
        list.add(1, "sample2");

        list.set(0, "sample to remove");
        list.remove("sample to remove");

        printAllListElements(list);

        System.out.println(String.format("Size of list = %s", list.size()));

        System.out.println(String.format("Second element of list = %s", list.get(1)));

        System.out.println(String.format("Has sample3 object?: %s", list.contains("sample3")));

        System.out.println(String.format("Index of sample3 = %s", list.indexOf("sample3")));

        System.out.println(String.format("Is list empty: %s", list.isEmpty()));

        List<String> subList = list.subList(1, 2);

        printAllListElements(subList);
    }

    /**
     * add element - O(1)
     * get element by index - O(1)
     * remove element - O(n)
     * contains value - O(n)
     */
    private static List<String> createArrayList() {
        return new ArrayList<>();
    }

    /**
     * add element - O(1)
     * get element by index - O(n)
     * remove element - O(1)
     * contains value - O(n)
     */
    private static List<String> createLinkedList() {
        return new LinkedList<>();
    }

    private static void printAllListElements(List<String> subList) {
        System.out.println("Elements in list are: ");
        for (String subListItem : subList) {
            System.out.println(subListItem);
        }
    }
}
