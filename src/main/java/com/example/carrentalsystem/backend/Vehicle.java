package com.example.carrentalsystem.backend;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

public class Vehicle implements Serializable {
    private String vehicleID;
    private String brand;
    private String model;
    private double dailyRate;
    public boolean isAvailable;
    private String imagePath;
    private String speed;
    private String transmission;
    private String type;

    
    public Vehicle(String vehicleID, String brand, String model, String transmission, String speed, boolean available, double dailyRate, String imagePath, String type) {
        this.vehicleID = vehicleID;
        this.brand = brand;
        this.model = model;
        this.transmission = transmission;
        this.speed = speed;
        this.dailyRate = dailyRate;
        this.isAvailable = true;
        this.imagePath = imagePath;
        this.type = type;

    }

    public boolean setAvailable(boolean bool) {
        return bool;
    }
    public String getVehicleID() {
        return vehicleID;
    }
    public String getImagePath() {
        return imagePath;
    }

    public String getBrand() {
        return brand;
    }
    public boolean getIsAvailable() {
        return isAvailable;
    }

    public String getModel() {
        return model;
    }

    public String getSpeed() {
        return speed;
    }
    public String getType() {
        return type;
    }

    public String getTransmission() {
        return "Transmission: " + transmission;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public String getDailyRateFormatted() {
        DecimalFormat df = new DecimalFormat("#,###");
        return "Mk " + df.format(dailyRate);
    }

    @Override
    public String toString() {
        return "Brand=" + brand + ", Model=" + model + ", DailyRate=" + getDailyRateFormatted();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return vehicleID.equals(vehicle.vehicleID);
    }

    @Override
    public int hashCode() {
        return vehicleID.hashCode();
    }

}

