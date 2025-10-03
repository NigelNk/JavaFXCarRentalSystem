package com.example.carrentalsystem.backend;

public class Truck extends Vehicle {
    private double cargoWeight;

    public Truck (String vehicleID, String brand, String model, String transmission, String speed, boolean available, double dailyRate, double cargoWeight, String image, String type) {
        super(vehicleID, brand, model, transmission, speed, available, dailyRate, image, type);
        this.cargoWeight = cargoWeight;
    }


    public double getCargoWeight() {
        return cargoWeight;
    }
}
