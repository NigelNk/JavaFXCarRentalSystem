package com.example.carrentalsystem;

import com.example.carrentalsystem.backend.Vehicle;
import com.example.carrentalsystem.backend.VehicleDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Objects;

import static com.example.carrentalsystem.HelloApplication.countAvailableVehicle;

public class HelloController {
    @FXML
    public Label customersAvailable;
    @FXML
    Label carsAvailabilty;
    @FXML
    private StackPane contentArea;
    @FXML
    AnchorPane root;
    @FXML
    private FlowPane vehiclesContainer;

    @FXML
    private VBox sidebar;

    @FXML
    protected void initialize() {
        VehicleDatabase vehicleDatabase = new VehicleDatabase();
        displayVehicles(VehicleDatabase.vehicles);
    }

    @FXML
    protected void addVehicle(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-vehicle.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());

    }

    @FXML
    protected void addCustomer(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("add-customer-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());
    }

    @FXML
    protected void customerView(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customer-view.fxml")));
        contentArea.getChildren().setAll(view);
        setActiveButton((Button) event.getSource());


    }

    @FXML
    protected void rent(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rent.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());
    }

    @FXML
    protected void viewVehicles(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vehicles-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());

    }
    @FXML
    protected void viewVehicles_2(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vehicles-view.fxml")));
        contentArea.getChildren().setAll(view);

    }

    @FXML
    public void rentedVehicle(ActionEvent event) throws IOException {
        Parent view = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rented-vehicle-view.fxml")));
        contentArea.getChildren().setAll(view);

        setActiveButton((Button) event.getSource());
    }

    private void setActiveButton(Button activeBtn) {
        for (Node node : sidebar.getChildren()) {
            if (node instanceof Button button) {
                button.getStyleClass().remove("active");
            }
        }
        activeBtn.getStyleClass().add("active");
        }

    public void displayVehicles(List<Vehicle> vehicles) {
        try {
            vehiclesContainer.getChildren().clear();

            for (int i = 0; i < 3; i++) {

                VBox card = new VBox(5);
                card.setAlignment(Pos.TOP_CENTER);
                card.setPrefSize(334, 441);
                card.setStyle("-fx-background-color: #ffffff; -fx-padding: 12; -fx-background-radius: 12; "
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 6, 0.4, 0, 1);");

                // Image
                ImageView imageView = new ImageView(new Image(new File(vehicles.get(i).getImagePath()).toURI().toString()));
                imageView.setFitHeight(193);
                imageView.setFitWidth(309);
                imageView.setPreserveRatio(true);

                Label nameLbl = new Label(vehicles.get(i).getBrand());
                nameLbl.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #2196F3;");

                Label modelLbl = new Label("Model: " + vehicles.get(i).getModel());
                modelLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

                Label speedLbl = new Label("Speed: " + vehicles.get(i).getSpeed());
                speedLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

                Label transmissionLbl = new Label(vehicles.get(i).getTransmission());
                transmissionLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");

                Label rateLbl = new Label("Daily Rate: " + vehicles.get(i).getDailyRateFormatted());
                rateLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #0bd5ac;");

                Label availableLbl = new Label("Is Available: " + vehicles.get(i).getIsAvailable());
                availableLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #878684;");


                Button rentBtn = new Button("Rent It");
                rentBtn.setPrefSize(77, 30);
                rentBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
//            rentBtn.setOnAction(e -> handleRentVehicle(v));

                // Add components to card
                card.getChildren().addAll(imageView, nameLbl, modelLbl, speedLbl, transmissionLbl, availableLbl, rateLbl, rentBtn);

                vehiclesContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    String imagePath = selectedFile.getAbsolutePath();
// When loading vehicle from DB
//public void loadVehicleImage(String imagePath) {
//    File file = new File(imagePath);
//    if (file.exists()) {
//        Image image = new Image(file.toURI().toString());
//        vehicleImageView.setImage(image);
//    }
//}
}

