package com.example.carrentalsystem.backend;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

public class VehicleDatabase {

    public  static ArrayList<Vehicle> vehicles;

    public VehicleDatabase() {
        vehicles = new ArrayList<>();
        populateArrayList();
    }

    public void populateArrayList() {
        try {
            FileInputStream file = new FileInputStream("vehicles.dat");
            ObjectInputStream inputFile = new ObjectInputStream(file);

            boolean endOfFile = false;
            while (!endOfFile) {
                try {

                    vehicles.add((Vehicle) inputFile.readObject());
                } catch(EOFException e) {
                    endOfFile = true;
                } catch (Exception  e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
            inputFile.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveVehicles() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("vehicles.dat"))) {
            for (Vehicle v : vehicles) {
                out.writeObject(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

