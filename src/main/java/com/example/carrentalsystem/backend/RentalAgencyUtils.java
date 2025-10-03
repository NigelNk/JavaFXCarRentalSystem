package com.example.carrentalsystem.backend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RentalAgencyUtils {

    private static final String RENTED_VEHICLES_FILE = "rentedVehicles.dat";

//   Saves rented vehicles list to file
    public static void saveRentedVehicles(ArrayList<Rental> rentedVehicles) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(RENTED_VEHICLES_FILE))) {
            out.writeObject(rentedVehicles);
        } catch (IOException e) {
            System.err.println("Error saving rented vehicles: " + e.getMessage());
        }
    }

}

