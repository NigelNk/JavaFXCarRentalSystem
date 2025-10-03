package com.example.carrentalsystem.backend;

import java.io.Serializable;
import java.util.Date;

public class Rental implements Serializable {
    private Date startDate;
    private Date endDate;
    Customer customer;
    Vehicle vehicle;

    public  Rental( Customer customer, Vehicle vehicle, Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
        this.vehicle = vehicle;

    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getCustomerID() {
        return customer.getCustomerID();
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public String getVehicleID() {
        return vehicle.getVehicleID();
    }
    public  String getBrand() {
        return vehicle.getBrand();
    }
    public double calculateRentalFee(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();

        // Convert to days, rounding up
        long days = (long) Math.ceil(diffInMillis / (1000.0 * 60 * 60 * 24));

        // Minimum 1 day charge
        if (days <= 0) {
            days = 1;
        }

        return vehicle.getDailyRate() * days;
    }

    public String getRentalFee() {
        return "" + (calculateRentalFee(startDate, endDate));
    }
}

