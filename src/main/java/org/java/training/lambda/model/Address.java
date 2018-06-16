package org.java.training.lambda.model;

import java.util.ArrayList;
import java.util.List;

public class Address {

    private String postCode;
    private int houseNumber;
    private String street;
    private String town;
    private List<String> livingPeople;

    public Address() {
    }

    public Address(String postCode, int houseNumber, String street, String town) {
        this(postCode, houseNumber, street, town, new ArrayList<>());
    }

    public Address(String postCode, int houseNumber, String street, String town, List<String> livingPeople) {
        this.postCode = postCode;
        this.houseNumber = houseNumber;
        this.street = street;
        this.town = town;
        this.livingPeople = livingPeople;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public List<String> getLivingPeople() {
        return livingPeople;
    }

    public void setLivingPeople(List<String> livingPeople) {
        this.livingPeople = livingPeople;
    }
}
