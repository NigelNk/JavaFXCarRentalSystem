package com.example.carrentalsystem.backend;

import java.util.Date;

public interface Rentable {
    void rentVehicle(Vehicle vehicle, Customer customer, Date startDate, Date endDate);
    void returnRentedVehicles(Vehicle vehicle, Customer customer);
}

