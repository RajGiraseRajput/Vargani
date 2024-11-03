package com.function_list.vargani.model;

public class Marriage_List_Model {

    private int id;
    private String name;
    private String amount;
    private String sarees;
    private String address;

    public Marriage_List_Model(int id,String name, String amount, String sarees, String address) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.sarees = sarees;
        this.address = address;
    }

    public Marriage_List_Model(int id,String name, String amount, String address) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.address = address;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSarees() {
        return sarees;
    }

    public void setSarees(String sarees) {
        this.sarees = sarees;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
