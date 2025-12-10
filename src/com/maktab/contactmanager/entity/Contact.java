package com.maktab.contactmanager.entity;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;



    public Contact( String name, String phoneNumber){

        this.name=name;
        this.phoneNumber=phoneNumber;

    }

    public Contact() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber;
    }

}

