package org.java.training.lambda;

import javafx.beans.binding.ObjectExpression;

import java.util.*;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class LambdaAndStreamExample {

    private static final Random RANDOM = new Random(100000);

    public static void main(String[] args) {

        testMethodRepeatsInStream();
    }

    private static void testSomeMethodsFoStream() {
        Set<String> setOfCities =
                Stream.of(Arrays.asList("London", "Bristol", "Swansea"), Arrays.asList("Edinburgh", "Aberdeen"),
                        Arrays.asList("Bristol", "Leeds"), Arrays.asList("Aberdeen"))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toCollection(LinkedHashSet::new));

        boolean areAllCitiesBristol =
                setOfCities.stream()
                        .allMatch(city -> {
                            System.out.println(city);
                            return city.equals("Bristol");
                        });
        System.out.println("Are all cities Bristol: " + areAllCitiesBristol);

        boolean hasBristol =
                setOfCities.stream()
                        .anyMatch(city -> {
                            System.out.println(city);
                            return city.equals("Bristol");
                        });
        System.out.println("Has Bristol: " + hasBristol);

        boolean hasNotManchester =
                setOfCities.stream()
                        .noneMatch(city -> {
                            System.out.println(city);
                            return city.equals("Manchester");
                        });
        System.out.println("Has no Manchester: " + hasNotManchester);

        String appendResult =
                setOfCities.stream()
                        .limit(5)
                        .sorted()
                        .reduce("", (city1, city2) -> {
                            System.out.println(city2);
                            return city1 + city2 + ", ";
                        });
        System.out.println(appendResult);

        Map<String, List<Ticket>> ticketsForEvent =
                Stream.concat(
                        Stream.of(new Ticket("a1001", "event1"), new Ticket("a1002", "event1")),
                        Stream.of(new Ticket("b2001", "event2"), new Ticket("a1003", "event1"), new Ticket("a1001", "event1")))
                        .distinct()
                        .collect(Collectors.groupingBy(Ticket::getEvent));
        System.out.println(ticketsForEvent);
    }

    private static void testMethodRepeatsInStream() {
        Stream<Integer> integersStream = Stream.of(3, 4, 7, 87, 32, 11, 6, 0, 54, 55)
                .filter(v -> {
                    System.out.println("Filter: " + v);
                    return v >= 11;
                })
                .map(v -> {
                    System.out.println("Map: " + v);
                    return 2 * v;
                })
                .sorted((v1, v2) -> {
                    System.out.println("Sort: " + v1 + " - " + v2);
                    return v1.compareTo(v2);
                })
                .distinct();

        integersStream.collect(Collectors.toList());

        // you can't re-use the same Stream after one of terminal methods was called (ex. collect(...)
        //System.out.println("Max = " + integersStream.max(Integer::compareTo));
    }

    private static void testSkipItems() {
        Stream<String> skipStream = Stream.of("a", "b", "c", "d", "e", "f", "g").skip(3);
        System.out.println(skipStream.collect(Collectors.toList()));

        List<Integer> collect =
                Stream.generate(() -> RANDOM.nextInt())
                        .limit(1_000_000)  // limit generation of items to 1kk
                        .skip(100000)  // skip first 100k items
                        .collect(Collectors.toList());
        System.out.println(collect.size());
    }

    private static void testParallelStream() {
        int maxValue = 10000000;

        long startTime = System.currentTimeMillis();
        System.out.println(countPrimes(maxValue, false));

        System.out.println("Total time passed: " + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();

        System.out.println(countPrimes(maxValue, true));

        System.out.println("Total time passed: " + (System.currentTimeMillis() - startTime));
    }

    private static long countPrimes(long max, boolean isParallel) {
        LongPredicate filterPrimeNumber =
                (n) -> n > 1 && LongStream.rangeClosed(2, (long) Math.sqrt(n)).noneMatch(divisor -> n % divisor == 0);

        if (isParallel) {
            return LongStream.range(1, max)
                    .parallel()
                    .filter(filterPrimeNumber)
                    .count();
        } else {
            return LongStream.range(1, max)
                    .filter(filterPrimeNumber)
                    .count();
        }
    }

    private static class Ticket implements Comparable<Ticket> {
        private String code;
        private String event;

        public Ticket(String code, String event) {
            this.code = code;
            this.event = event;
        }

        public String getCode() {
            return code;
        }

        public String getEvent() {
            return event;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Ticket)) return false;

            Ticket ticket = (Ticket) o;

            if (getCode() != null ? !getCode().equals(ticket.getCode()) : ticket.getCode() != null) return false;
            return getEvent() != null ? getEvent().equals(ticket.getEvent()) : ticket.getEvent() == null;
        }

        @Override
        public int hashCode() {
            int result = getCode() != null ? getCode().hashCode() : 0;
            result = 31 * result + (getEvent() != null ? getEvent().hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Ticket{" +
                    "code='" + code + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Ticket ticket) {
            return -this.getEvent().compareTo(ticket.getEvent());
        }
    }

}
