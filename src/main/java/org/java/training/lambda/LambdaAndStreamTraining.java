package org.java.training.lambda;

import org.java.training.lambda.model.Address;
import org.java.training.lambda.model.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class LambdaAndStreamTraining {

    public Collection<Integer> getEvenNumbers(Collection<Integer> integers) {
        return integers.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
    }

    public Collection<Integer> getDistinctNumbers(List<Integer> input) {
        return null;
    }

    public Collection<String> getFilteredPostCodesWithAscendingOrdering(Collection<String> postCodes, String filterCriteria) {
        return null;
    }

    public Collection<String> getFilteredPostCodesWithDescendingOrdering(Collection<String> postCodes, String filterCriteria) {
        return null;
    }

    public Collection<Address> getAddressesOfPostCode(Map<String, List<Address>> addresses, String postCode) {
        return null;
    }

    public boolean isAddressesFromTown(List<Address> addresses, String townToVerify) {
        return false;
    }

    public Optional<Integer> getMaxHouseNumber(List<Address> addresses) {
        return Optional.empty();
    }

    public Map<String, Integer> getNumberOfAddressesPerPostCode(Map<String, List<Address>> addresses) {
        return null;
    }

    public Collection<String> getPostCodesFromTown(Map<String, List<Address>> addresses, String town) {
        return null;
    }

    public String getAddressesAsSingleString(List<Address> addresses) {
        return null;
    }

    public Map<String, String> getPostCodesPerTownFromAddresses(List<Address> addresses) {
        return null;
    }

    public List<String> getAllLivingPeopleInTown(List<Address> addresses, String town) {
        return null;
    }

    public Transaction getTotalAmountOfTransactionsOfBank(List<Transaction> transactions, String fromBank, String toBank) {
        return null;
    }

    public Transaction getLastTransaction(List<Transaction> transactions) {
        return null;
    }

    public Map<String, Long> getTotalTransactionsGroupedBySender(List<Transaction> transactions) {
        return null;
    }

    public Map<String, Map<String, Double>> getSumOfAllTransactionsGroupedBySenderAndReceiver(List<Transaction> transactions) {
        return null;
    }

    public Map<String, String> getTransactionsAsStringGroupedBySender(List<Transaction> transactions) {
        // the result should look like (ex.): Bank1 -> Bank2 = 100, Bank3 = 150,
        // where Bank1 is a key and 'Bank2 = 100, Bank3 = 150' is a String value
        return null;
    }
}
