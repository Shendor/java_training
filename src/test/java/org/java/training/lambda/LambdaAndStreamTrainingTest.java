package org.java.training.lambda;

import com.vocalink.training.lambda.model.Address;
import com.vocalink.training.lambda.model.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LambdaAndStreamTrainingTest {

    private LambdaAndStreamTraining lambdaTraining;

    @Before
    public void setUp() throws Exception {
        lambdaTraining = new LambdaAndStreamTraining();
    }

    @Test
    public void testGetEvenNumbers() throws Exception {
        List<Integer> input = Stream.of(3, 5, 1, 12, 4, 89, 90, 0, 32, 1).collect(Collectors.toList());

        Collection<Integer> evenNumbers = lambdaTraining.getEvenNumbers(input);

        assertEquals(5, evenNumbers.size());
        assertTrue(evenNumbers.containsAll(Stream.of(12, 4, 90, 0, 32).collect(Collectors.toList())));
    }

    @Test
    public void testGetDistinctNumbers() throws Exception {
        List<Integer> input = Stream.of(3, 5, 1, 5, 4, 3, 90, 3, 4, 1).collect(Collectors.toList());

        Collection<Integer> distinctNumbers = lambdaTraining.getDistinctNumbers(input);

        assertEquals(5, distinctNumbers.size());
        assertTrue(distinctNumbers.containsAll(Stream.of(3, 5, 4, 90, 1).collect(Collectors.toList())));
    }

    @Test
    public void testGetStringsWithWordSW_ThenSortedAscending() throws Exception {
        List<String> input = Stream.of("DF1 4RT", "SW5 1CC", "TF5 2BE", "NB4 9TT", "SW1 3FX", "SW8 2YX", "WV9 8VV").collect(Collectors.toList());

        Collection<String> postCodes = lambdaTraining.getFilteredPostCodesWithAscendingOrdering(input, "SW");

        Deque<String> expectedPostCodes = new ArrayDeque<>();
        expectedPostCodes.add("SW1 3FX");
        expectedPostCodes.add("SW5 1CC");
        expectedPostCodes.add("SW8 2YX");

        assertEquals(3, postCodes.size());
        postCodes.forEach(actualPostCode -> assertEquals(expectedPostCodes.poll(), actualPostCode));
    }

    @Test
    public void testGetStringsWithWordSW_ThenSortedDescending() throws Exception {
        List<String> input = Stream.of("SW5 1CC", "NB4 9TT", "SW1 3FX", "SW8 2YX").collect(Collectors.toList());

        Collection<String> postCodes = lambdaTraining.getFilteredPostCodesWithDescendingOrdering(input, "SW");

        Deque<String> expectedPostCodes = new ArrayDeque<>();
        expectedPostCodes.add("SW8 2YX");
        expectedPostCodes.add("SW5 1CC");
        expectedPostCodes.add("SW1 3FX");

        assertEquals(3, postCodes.size());
        postCodes.forEach(actualPostCode -> assertEquals(expectedPostCodes.poll(), actualPostCode));
    }

    @Test
    public void testGetAllAddressesForCodeSW() throws Exception {
        Map<String, List<Address>> addresses =
                Stream.of(
                        new Address("SW5 1CC", 12, "Street 5", "SWTown"),
                        new Address("WD12 7GG", 14, "Street 14", "WDTown"),
                        new Address("SW1 3FX", 1, "Street 1", "SWTown"),
                        new Address("SW24 9DN", 51, "Street 51", "SWTown"),
                        new Address("NB4 9TT", 109, "Street 100", "NBTown"),
                        new Address("SW5 1CC", 21, "Street 5", "SWTown"),
                        new Address("SW1 3FX", 76, "Street 1", "SWTown"))
                        .collect(Collectors.groupingBy(Address::getPostCode));

        Collection<Address> filteredAddresses = lambdaTraining.getAddressesOfPostCode(addresses, "SW");

        assertEquals(5, filteredAddresses.size());
    }

    @Test
    public void testIfAddressesAreFromSameTown() throws Exception {
        List<Address> addresses =
                Stream.of(
                        new Address("SW5 1CC", 12, "Street 5", "SWTown"),
                        new Address("SW1 3FX", 1, "Street 1", "SWTown"),
                        new Address("SW24 9DN", 51, "Street 51", "SWTown"),
                        new Address("SW5 1CC", 21, "Street 5", "SWTown"),
                        new Address("SW1 3FX", 76, "Street 1", "SWTown"))
                        .collect(Collectors.toList());

        assertTrue(lambdaTraining.isAddressesFromTown(addresses, "SWTown"));
        assertTrue(lambdaTraining.isAddressesFromTown(addresses, "SWTown".toLowerCase()));
    }

    @Test
    public void testGetMaxHouseNumber() throws Exception {
        List<Address> addresses =
                Stream.of(
                        new Address("SW5 1CC", 12, "Street 5", "SWTown"),
                        new Address("SW1 3FX", 151, "Street 1", "SWTown"),
                        new Address("SW24 9DN", 51, "Street 51", "SWTown"),
                        new Address("SW5 1CC", 21, "Street 5", "SWTown"),
                        new Address("SW1 3FX", 76, "Street 1", "SWTown"))
                        .collect(Collectors.toList());

        assertEquals(151, lambdaTraining.getMaxHouseNumber(addresses).orElse(0).intValue());
    }

    @Test
    public void testGetTotalNumberOfAddressesPerPostCode() throws Exception {
        Map<String, List<Address>> addresses =
                Stream.of(
                        new Address("SW5 1CC", 12, "Street 5", "SWTown"),
                        new Address("WD12 7GG", 14, "Street 14", "WDTown"),
                        new Address("SW1 3FX", 1, "Street 1", "SWTown"),
                        new Address("SW5 1CC", 109, "Street 100", "NBTown"),
                        new Address("SW5 1CC", 21, "Street 5", "SWTown"),
                        new Address("SW1 3FX", 76, "Street 1", "SWTown"))
                        .collect(Collectors.groupingBy(Address::getPostCode));

        Map<String, Integer> filteredAddresses = lambdaTraining.getNumberOfAddressesPerPostCode(addresses);

        assertEquals(3, filteredAddresses.get("SW5 1CC").intValue());
        assertEquals(2, filteredAddresses.get("SW1 3FX").intValue());
        assertEquals(1, filteredAddresses.get("WD12 7GG").intValue());
    }

    @Test
    public void testGetPostCodesForTown() throws Exception {
        Map<String, List<Address>> addresses =
                Stream.of(
                        new Address("SW5 5CC", 12, "Street 5", "SWTown"),
                        new Address("WD12 7GG", 14, "Street 14", "WDTown"),
                        new Address("SR1 78DC", 1, "Street 1", "SWTown"),
                        new Address("SW5 1CC", 109, "Street 100", "SWTown"),
                        new Address("SH12 6UN", 21, "Street 5", "SWTown"),
                        new Address("SW5 1CC", 76, "Street 1", "SWTown"))
                        .collect(Collectors.groupingBy(Address::getPostCode));

        Collection<String> postCodes = lambdaTraining.getPostCodesFromTown(addresses, "SWTown");

        assertEquals(4, postCodes.size());
        assertTrue(postCodes.containsAll(Arrays.asList("SW5 5CC", "SR1 78DC", "SW5 1CC", "SH12 6UN")));
    }

    @Test
    public void testMapAddressesToPostCodeAndTownInSingleLine() throws Exception {
        List<Address> addresses =
                Stream.of(
                        new Address("SW5 1CC", 12, "Street 5", "SWTown"),
                        new Address("SW1 3FX", 151, "Street 1", "SWTown"))
                        .collect(Collectors.toList());

        String addressesAsString = lambdaTraining.getAddressesAsSingleString(addresses);

        assertEquals("[SW5 1CC - SWTown], [SW1 3FX - SWTown]", addressesAsString);
    }

    @Test
    public void testMapAddressesToPostCodesInTown() throws Exception {
        List<Address> addresses =
                Stream.of(
                        new Address("SW12 4FR", 1, "Street 1", "SWTown"),
                        new Address("RT4 8IT", 2, "Street 2", "RTTown"),
                        new Address("SW7 12PU", 3, "Street 1", "SWTown"),
                        new Address("RT13 9NN", 3, "Street 2", "RTTown"),
                        new Address("DF1 3FX", 4, "Street 3", "DFTown"))
                        .collect(Collectors.toList());

        Map<String, String> postCodesPerTown = lambdaTraining.getPostCodesPerTownFromAddresses(addresses);

        assertEquals("SW12 4FR, SW7 12PU", postCodesPerTown.get("SWTown"));
        assertEquals("RT4 8IT, RT13 9NN", postCodesPerTown.get("RTTown"));
        assertEquals("DF1 3FX", postCodesPerTown.get("DFTown"));
    }

    @Test
    public void testGetAllLivingPeopleInTown() throws Exception {
        List<Address> addresses =
                Stream.of(
                        new Address("SW12 4FR", 2, "Street 1", "SWTown", Arrays.asList("person 1", "person 2")),
                        new Address("RT4 8IT", 12, "Street 2", "RTTown", Collections.singletonList("person 3")),
                        new Address("SW7 12PU", 54, "Street 1", "SWTown", Arrays.asList("person 4", "person 5", "person 6")))
                        .collect(Collectors.toList());

        List<String> livingPeople = lambdaTraining.getAllLivingPeopleInTown(addresses, "SWTown");

        assertEquals(5, livingPeople.size());
        assertTrue(livingPeople.containsAll(Arrays.asList("person 1", "person 2", "person 4", "person 5", "person 6")));
    }

    @Test
    public void testGetTotalAmountOfTransactionsForBank() throws Exception {
        List<Transaction> transactions =
                Stream.of(
                        new Transaction("Bank1", "Bank2", 10f),
                        new Transaction("Bank1", "Bank2", 120f),
                        new Transaction("Bank2", "Bank1", 100f),
                        new Transaction("Bank1", "Bank3", 220f))
                        .collect(Collectors.toList());

        Transaction transaction = lambdaTraining.getTotalAmountOfTransactionsOfBank(transactions, "Bank1", "Bank2");

        assertEquals(130f, transaction.getAmount(), 0.001f);
    }

    @Test
    public void testGetLastTransaction() throws Exception {
        List<Transaction> transactions =
                Stream.of(
                        new Transaction("Bank1", "Bank2", 10f),
                        new Transaction("Bank1", "Bank2", 120f),
                        new Transaction("Bank2", "Bank1", 100f),
                        new Transaction("Bank1", "Bank3", 220f))
                        .collect(Collectors.toList());

        Transaction transaction = lambdaTraining.getLastTransaction(transactions);

        assertEquals(220f, transaction.getAmount(), 0.001f);
    }

    @Test
    public void testGetTotalTransactionsGroupedBySender() throws Exception {
        List<Transaction> transactions =
                Stream.of(
                        new Transaction("Bank1", "Bank2", 10f),
                        new Transaction("Bank1", "Bank2", 120f),
                        new Transaction("Bank2", "Bank1", 100f),
                        new Transaction("Bank1", "Bank3", 220f),
                        new Transaction("Bank2", "Bank1", 51f))
                        .collect(Collectors.toList());

        Map<String, Long> totalTransactions = lambdaTraining.getTotalTransactionsGroupedBySender(transactions);

        assertEquals(2, totalTransactions.size());
        assertEquals(3, totalTransactions.get("Bank1").intValue());
        assertEquals(2, totalTransactions.get("Bank2").intValue());
    }

    // ============== For masochists only! - uncomment @Test for these 2 tests ==============

     @Test
    public void testGetSumOfAllTransactionsGroupedBySenderAndReceiver() throws Exception {
        List<Transaction> transactions =
                Stream.of(
                        new Transaction("Bank1", "Bank2", 10f),
                        new Transaction("Bank1", "Bank2", 120f),
                        new Transaction("Bank2", "Bank3", 100f),
                        new Transaction("Bank1", "Bank3", 220f),
                        new Transaction("Bank2", "Bank4", 60f),
                        new Transaction("Bank2", "Bank1", 10f),
                        new Transaction("Bank2", "Bank1", 51f))
                        .collect(Collectors.toList());

        Map<String, Map<String, Double>> totalTransactions = lambdaTraining.getSumOfAllTransactionsGroupedBySenderAndReceiver(transactions);

        assertEquals(2, totalTransactions.size());
        assertEquals(2, totalTransactions.get("Bank1").size());
        assertEquals(3, totalTransactions.get("Bank2").size());
        assertEquals(130d, totalTransactions.get("Bank1").get("Bank2"), 0.001);
        assertEquals(220d, totalTransactions.get("Bank1").get("Bank3"), 0.001);
        assertEquals(100d, totalTransactions.get("Bank2").get("Bank3"), 0.001);
        assertEquals(60d, totalTransactions.get("Bank2").get("Bank4"), 0.001);
        assertEquals(61d, totalTransactions.get("Bank2").get("Bank1"), 0.001);
    }

     @Test
    public void testGetTransactionsAsStringGroupedBySender() throws Exception {
        List<Transaction> transactions =
                Stream.of(
                        new Transaction("Bank1", "Bank2", 10f),
                        new Transaction("Bank1", "Bank2", 120f),
                        new Transaction("Bank2", "Bank3", 100f),
                        new Transaction("Bank1", "Bank3", 220f),
                        new Transaction("Bank2", "Bank4", 60f),
                        new Transaction("Bank2", "Bank1", 10f),
                        new Transaction("Bank2", "Bank1", 51f))
                        .collect(Collectors.toList());

        Map<String, String> groupedTransactions = lambdaTraining.getTransactionsAsStringGroupedBySender(transactions);

        assertEquals(2, groupedTransactions.size());
        assertEquals("Bank2 = 10.0, Bank2 = 120.0, Bank3 = 220.0", groupedTransactions.get("Bank1"));
        assertEquals("Bank3 = 100.0, Bank4 = 60.0, Bank1 = 10.0, Bank1 = 51.0", groupedTransactions.get("Bank2"));
    }
}