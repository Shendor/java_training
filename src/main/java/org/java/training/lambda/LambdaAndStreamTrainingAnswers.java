package org.java.training.lambda;

import org.java.training.lambda.model.Address;
import org.java.training.lambda.model.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class LambdaAndStreamTrainingAnswers {

    public Collection<Integer> getEvenNumbers(Collection<Integer> integers) {
        return integers.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toList());
    }

    public Collection<Integer> getDistinctNumbers(List<Integer> input) {
        return input.stream().distinct().collect(Collectors.toList());
    }

    public Collection<String> getFilteredPostCodesWithAscendingOrdering(Collection<String> postCodes, String filterCriteria) {
        return postCodes.stream()
                .filter(postCode -> postCode.contains(filterCriteria))
                .sorted()
                .collect(Collectors.toList());
    }

    public Collection<String> getFilteredPostCodesWithDescendingOrdering(Collection<String> postCodes, String filterCriteria) {
        return postCodes.stream()
                .filter(postCode -> postCode.contains(filterCriteria))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    public Collection<Address> getAddressesOfPostCode(Map<String, List<Address>> addresses, String postCode) {
        return addresses.entrySet().stream()
                .filter(entry -> entry.getKey().contains(postCode))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean isAddressesFromTown(List<Address> addresses, String townToVerify) {
        return addresses.stream().allMatch(address -> address.getTown().equalsIgnoreCase(townToVerify));
    }

    public Optional<Integer> getMaxHouseNumber(List<Address> addresses) {
        return addresses.stream().map(Address::getHouseNumber).max(Integer::compareTo);
    }

    public Map<String, Integer> getNumberOfAddressesPerPostCode(Map<String, List<Address>> addresses) {
        return addresses.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().size()));
    }

    public Collection<String> getPostCodesFromTown(Map<String, List<Address>> addresses, String town) {
        return addresses.values().stream()
                .flatMap(Collection::stream)
                .filter(address -> address.getTown().equals(town))
                .map(Address::getPostCode)
                .distinct()
                .collect(Collectors.toList());
    }

    public String getAddressesAsSingleString(List<Address> addresses) {
        return addresses.stream()
                .map(address -> String.format("[%s - %s]", address.getPostCode(), address.getTown()))
                .collect(Collectors.joining(", "));
    }

    public Map<String, String> getPostCodesPerTownFromAddresses(List<Address> addresses) {
        return addresses.stream()
                .collect(Collectors.toMap(Address::getTown, Address::getPostCode, (a1, a2) -> a1 + ", " + a2));
    }

    public List<String> getAllLivingPeopleInTown(List<Address> addresses, String town) {
        return addresses.stream()
                .filter(address -> address.getTown().equals(town))
                .flatMap(address -> address.getLivingPeople().stream())
                .collect(Collectors.toList());
    }

    public Transaction getTotalAmountOfTransactionsOfBank(List<Transaction> transactions, String fromBank, String toBank) {
        return transactions.stream()
                .filter(transaction -> transaction.getFrom().equals(fromBank) && transaction.getTo().equals(toBank))
                .reduce(new Transaction(), (t1, t2) -> {
                    t1.setAmount(t1.getAmount() + t2.getAmount());
                    return t1;
                });
    }

    public Transaction getLastTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .reduce((t1, t2) -> t2).orElse(new Transaction());
    }

    public Map<String, Long> getTotalTransactionsGroupedBySender(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getFrom, Collectors.counting()));
    }

    public Map<String, Map<String, Double>> getSumOfAllTransactionsGroupedBySenderAndReceiver(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getFrom,
                        Collectors.groupingBy(Transaction::getTo, Collectors.summingDouble(Transaction::getAmount))));
    }

    public Map<String, String> getTransactionsAsStringGroupedBySender(List<Transaction> transactions) {
        // the result should look like (ex.): Bank1 -> Bank2 = 100, Bank3 = 150,
        // where Bank1 is a key and 'Bank2 = 100, Bank3 = 150' is a String value
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getFrom,
                        Collectors.mapping(item -> item.getTo() + " = " + item.getAmount(), Collectors.joining(", "))));
    }
}
