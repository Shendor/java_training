package org.java.training.collections;

import java.util.*;

public class MapExample {

    public static void main(String[] args) {
        testImmutability();
    }

    /**
     * put element - O(1)
     * get element - O(1)
     * containsKey - O(1)
     * remove element - O(1)
     * iteration - O(n)
     */
    private static Map<String, List<Address>> createHashMap() {
        return new HashMap<>();
    }

    /**
     * put element - O(1)
     * get element - O(1)
     * containsKey - O(1)
     * remove element - O(1)
     * iteration - O(n)
     */
    private static Map<String, List<Address>> createLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * put element - O(log(n))
     * get element - O(log(n))
     * containsKey - O(log(n))
     * remove element - O(log(n))
     * iteration - O(n)
     */
    private static Map<String, List<Address>> createTreeMap() {
        return new TreeMap<>();
    }

    private static void testPerformance() {
        HashMap<SampleKey, Integer> map = new HashMap<>(10000, 0.9f);
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            map.put(new SampleKey(i, "name" + i), 2 * i);
        }
    }

    private static void testImmutability() {
        HashMap<SampleKey, Integer> map = new HashMap<>(4, 0.7f);
        SampleKey key = new SampleKey(1, "name");

        map.put(key, 100);

        System.out.println(map.get(key));

        key.setSize(890);

        System.out.println(map.get(key));
    }

    private static class SampleKey {
        private int size = 1;
        private String name;

        public SampleKey(int size, String name) {
            this.size = size;
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SampleKey)) return false;

            SampleKey sampleKey = (SampleKey) o;

            return size == sampleKey.size && name.equals(sampleKey.name);
        }

        @Override
        public int hashCode() {
            return size;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private static class Address {
        private String street;
        private String town;

        public Address(String street, String town) {
            this.street = street;
            this.town = town;
        }

        public String getStreet() {
            return street;
        }

        public String getTown() {
            return town;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "street='" + street + '\'' +
                    ", town='" + town + '\'' +
                    '}';
        }
    }
}
