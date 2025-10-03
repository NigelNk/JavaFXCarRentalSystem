package com.example.carrentalsystem;

import com.example.carrentalsystem.backend.Customer;
import com.example.carrentalsystem.backend.CustomerDatabase;
import com.example.carrentalsystem.backend.Vehicle;
import com.example.carrentalsystem.backend.VehicleDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class HelloApplication extends Application {
    public static int countAvailableVehicle = 0;
    public static int countAvailableCustomer = 0;
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Car Rental System");

//        initialize vehicle database and customer databsse
            VehicleDatabase database = new VehicleDatabase();
            CustomerDatabase customerDatabase = new CustomerDatabase();

            countAvailableCars(VehicleDatabase.vehicles);

//        load fxml for intitial dashboard
            HelloController controller = fxmlLoader.getController();

            controller.carsAvailabilty.setText("" + countAvailableVehicle);
            controller.customersAvailable.setText("" + CustomerController.countCustomers());

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void countAvailableCars (List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            countAvailableVehicle += 1;
        }
    }



}
