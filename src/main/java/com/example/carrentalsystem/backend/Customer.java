package com.example.carrentalsystem.backend;

import java.io.*;

public class Customer implements Serializable {
    private String customerID;
    private String name;
    private String phone;
    private String address;


    public  Customer(String name, String customerID, String phone,  String address) {
        this.name = name;
        this.customerID = customerID;
        this.phone = phone;
        this.address = address;

    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "customerID=" + customerID + ", name=" + name + ", phone=" + phone;
    }
}
