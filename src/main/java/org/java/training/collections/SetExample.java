package org.java.training.collections;

import java.util.*;

public class SetExample {

    public static void main(String[] args) {
        Set<Player> set = createHashSet();

        set.add(new Player(1001, "***johnny_iridescent***"));
        set.add(new Player(1002, "creepy_clown_91"));
        set.add(new Player(1003, "craZy_lollipop"));
        set.add(new Player(1004, "alien007"));
        set.add(new Player(1004, "alien007"));

        System.out.println(String.format("Size of set = %s", set.size()));
        printAllListElements(set);

        // bad practice!
        System.out.println(String.format("Second element of set = %s", new ArrayList<>(set).get(1)));

        System.out.println(String.format("Has craZy_lollipop player?: %s", set.contains(new Player(1003, "craZy_lollipop"))));

        System.out.println(String.format("Is set empty: %s", set.isEmpty()));

    }

    /**
     * add element - O(1)
     * remove element - O(1)
     * contains value - O(1)
     */
    private static Set<Player> createLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    /**
     * add element - O(1)
     * remove element - O(1)
     * contains value - O(1)
     */
    private static Set<Player> createHashSet() {
        return new HashSet<>();
    }

    /**
     * add element - O(log(n))
     * remove element - O(log(n))
     * contains value - O(log(n))
     */
    private static Set<Player> createTreeSet() {
        return new TreeSet<>();
    }

    private static void printAllListElements(Set<Player> set) {
        System.out.println("Elements in set are: ");
        for (Player item : set) {
            System.out.println(item);
        }
    }

    private static class Player {

        private long id;
        private String name;

        //hotkey to generate constructor: Alt + Insert -> Constructor

        public Player(long id, String name) {
            this.id = id;
            this.name = name;
        }

        //hotkey to generate getters and setters: Alt + Insert -> Getter and Setter

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        //hot-key to override equals and hashcode: Alt+Insert -> equals() and hashcode()

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Player)) return false;

            Player player = (Player) o;

            if (getId() != player.getId()) return false;
            return getName() != null ? getName().equals(player.getName()) : player.getName() == null;
        }

        @Override
        public int hashCode() {
            int result = (int) (getId() ^ (getId() >>> 32));
            result = 31 * result + (getName() != null ? getName().hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Player{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
