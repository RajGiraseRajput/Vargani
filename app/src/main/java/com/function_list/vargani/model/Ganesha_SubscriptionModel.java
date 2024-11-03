package com.function_list.vargani.model;

public class Ganesha_SubscriptionModel {
    private int id;
    private String name;
    private String amount;
    private String address;

    public Ganesha_SubscriptionModel(int id, String name, String amount, String address) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getAddress() {
        return address;
    }
}

