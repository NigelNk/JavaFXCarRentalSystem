package com.example.carrentalsystem;


import com.example.carrentalsystem.backend.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentedVehicleController implements Rentable {

    @FXML
    public TableColumn<Rental, Date> rentedDate;
    @FXML
    public TableColumn<Rental, Date> returnDate;
    @FXML
    private TableView<Rental> customerRentedTable;

    @FXML
    private TableColumn<Rental, String> customerID;

    @FXML
    private TableColumn<Rental, String> customerName;
    @FXML
    private TableColumn<Rental, String> vehicleID;
    @FXML
    private TableColumn<Rental, String> brand;
    @FXML
    private TableColumn<Rental, String> rentalFee;

    static ObservableList<Rental> rentalData = FXCollections.observableArrayList();
    static Vehicle vehicle;
    Rental selectedRental;
    @FXML
    protected void initialize() {
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        vehicleID.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        rentedDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        rentalFee.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));

        rentalData.clear();
        rentalData.addAll((loadRentedVehicles()));

        customerRentedTable.setItems(rentalData);

    }


    @SuppressWarnings("unchecked")
    public static List<Rental> loadRentedVehicles() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("rentedVehicles.dat"))) {
            return (List<Rental>) in.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No rented vehicles file found. Returning empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading rented vehicles: " + e.getMessage());
        }
        return new java.util.ArrayList<>();
    }

    @FXML
    public void returnVehicleBtn(ActionEvent actionEvent) {

        selectedRental = customerRentedTable.getSelectionModel().getSelectedItem();
        returnRentedVehicles(selectedRental.getVehicle(), selectedRental.getCustomer());

    }
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void rentVehicle(Vehicle vehicle, Customer customer, Date startDate, Date endDate) {
//        Renting is done in RentController
    }

    @Override
    public void returnRentedVehicles(Vehicle vehicle, Customer customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Return");
        alert.setHeaderText("Return Vehicle");
        alert.setContentText("Are you sure you want to mark this vehicle borrowed by " + customer.getName() + " as returned?");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-size: 18px; -fx-font-family: 'Arial';");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // TODO: Recreate vehicle object
                AddVehicleController.recreateVehicleObject(vehicle);
                customerRentedTable.getItems().remove(selectedRental);
                showVehicleReturnedAlert(vehicle);
                RentController.rentals.remove(selectedRental);

                updateRentals();

            } else {
                System.out.println("Action cancelled");
            }
        });
    }
    private void showVehicleReturnedAlert(Vehicle vehicle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vehicle Returned");
        alert.setHeaderText("Success!");
        alert.setContentText("The vehicle \"" + vehicle.getBrand() + "\" has been successfully Returned.");

        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.getDialogPane().setStyle(
                "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: #f9f9f9;" +
                        "-fx-text-fill: #2c3e50;"
        );

        alert.showAndWait();
    }
    private void updateRentals() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("rentedVehicles.dat"))) {
            for (Rental v : RentController.rentals) {
                out.writeObject(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
