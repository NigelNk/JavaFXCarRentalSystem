package com.example.carrentalsystem;

import com.example.carrentalsystem.backend.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class RentController implements Rentable{
    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<Vehicle> vehicleComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ImageView vehicleImage;

    private RentalAgency rentalAgency;
    private CustomerController customerController;

    static ArrayList<Rental> rentals;

    @FXML
    public void initialize() {
        rentalAgency = new RentalAgency();
        rentals = new ArrayList<>(RentedVehicleController.loadRentedVehicles()); // load existing rentals

        customerComboBox.setItems(loadCustomersFromFile());
        vehicleComboBox.getItems().addAll(VehicleDatabase.vehicles);
    }

    @FXML
    public void setPicture(ActionEvent actionEvent) {
        Vehicle selectedVehicle = vehicleComboBox.getValue();

        if (selectedVehicle != null && selectedVehicle.getImagePath() != null) {
            try {
                // Load image from file path or resources
                Image image = new Image("file:" + selectedVehicle.getImagePath());
                vehicleImage.setImage(image);
            } catch (Exception e) {
                showError("Error adding rental: " + e.getMessage());
            }
        } else {
            // Clear image if no selection or path null
            vehicleImage.setImage(null);
        }
    }



    @FXML
    private void handleAddRental() {
        try {
            Customer customer = customerComboBox.getSelectionModel().getSelectedItem();
            Vehicle vehicle = vehicleComboBox.getValue();
            LocalDate start = LocalDate.now();
            LocalDate end = endDatePicker.getValue();

            if (customer == null || vehicle == null || start == null || end == null) {
                showError("Please fill in all fields before adding rental.");
                return;
            }

            // Convert LocalDate to Date
            Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());



            rentVehicle(vehicle, customer, startDate, endDate);

            showSuccess();

        } catch (Exception e) {
            showError("Error adding rental: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Rental added successfully!");
        alert.showAndWait();
    }

    @SuppressWarnings("unchecked")
    private ObservableList<Customer> loadCustomersFromFile() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("myCustomerList.dat"))) {
            ArrayList<Customer> customers = (ArrayList<Customer>) in.readObject();
            list.addAll(customers);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void rentVehicle(Vehicle vehicle, Customer customer, Date startDate, Date endDate) {
        Rental rental = new Rental(customer, vehicle, startDate, endDate);
        rentals.add(rental);

        RentalAgencyUtils.saveRentedVehicles(rentals);

        // Save rented vehicles to file
        RentalAgencyUtils.saveRentedVehicles(rentals);

        VehicleDatabase.vehicles.remove(vehicle);
        VehicleDatabase.saveVehicles();
    }

    @Override
    public void returnRentedVehicles(Vehicle vehicle, Customer customer) {

// Nothing here, returning rental is done in RentedVehicle controller
    }


}
