package com.example.carrentalsystem;

import com.example.carrentalsystem.backend.Customer;
import com.example.carrentalsystem.backend.Vehicle;

public class CustomerRental {
    Customer customer;
    Vehicle vehicle;

    public CustomerRental(Customer customer, String name, Vehicle vehicle) {
        this.customer = customer;
        this.vehicle = vehicle;
    }

    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
}
