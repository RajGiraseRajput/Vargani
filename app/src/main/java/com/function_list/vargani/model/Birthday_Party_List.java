package com.function_list.vargani.model;

public class Birthday_Party_List {

    private int id;
    private String name;
    private String amount;
    private String gift;
    private String address;


    public Birthday_Party_List(int id,String name, String amount,String gift,String address){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.gift = gift;
        this.address = address;
    }

    public Birthday_Party_List(String name,String amount,String gift,String address){
        this.name = name;
        this.amount = amount;
        this.gift = gift;
        this.address = address;
    }


    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getAmount(){
        return amount;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getGift(){
        return gift;
    }
    public void setGift(String gift){
        this.gift = gift;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
}
