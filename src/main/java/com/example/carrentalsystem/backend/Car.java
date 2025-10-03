package com.example.carrentalsystem.backend;

public class Car extends Vehicle {
    private int capacity;

    public Car(String vehicleID, String brand, String model, String transmission, String speed, boolean available, double dailyRate, int capacity, String image, String type) {
        super(vehicleID, brand, model, transmission, speed, available, dailyRate, image, type);
        this.capacity = capacity;

    }

    public int getCapacity() {
        return capacity;
    }
}
