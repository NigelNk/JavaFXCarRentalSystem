package com.example.carrentalsystem;

import com.example.carrentalsystem.backend.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.carrentalsystem.backend.VehicleDatabase.vehicles;

public class AddVehicleController {

    @FXML
    public TextField vehicleIDField;
    @FXML
    public TextField brandField;
    @FXML
    public TextField modelField;
    @FXML
    public TextField transmission;
    @FXML
    public TextField speed;
    @FXML
    public ComboBox vehicleTypeBox;
    @FXML
    public CheckBox availabilityCheck;
    @FXML
    public TextField dailyRateField1;
    @FXML
    private ImageView vehicleImageView;


    private File selectedFile;
    private String storedImagePath;

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Vehicle Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        Stage stage = (Stage) vehicleImageView.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {

                File storageDir = new File("src/main/resources/com/example/carrentalsystem/images/");
                if (!storageDir.exists()) storageDir.mkdirs(); //

                String uniqueFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
                File destination = new File(storageDir, uniqueFileName);


                Files.copy(selectedFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

                storedImagePath = "src/main/resources/com/example/carrentalsystem/images/" + uniqueFileName;

                vehicleImageView.setImage(new Image(destination.toURI().toString()));

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to save image to storage folder.");
                alert.showAndWait();
            }
        }
    }


//    Save button
    @FXML
    private void handleSaveVehicle() {
        try {
            String vehicleID = vehicleIDField.getText();
            String brand = brandField.getText();
            String model = modelField.getText();
            String trans = transmission.getText();
            String spd = speed.getText();
            String type = (vehicleTypeBox.getValue() != null) ? vehicleTypeBox.getValue().toString() : "Unknown";
            boolean available = availabilityCheck.isSelected();
            double dailyRate = Double.parseDouble(dailyRateField1.getText());

            Vehicle newVehicle = null;
//            initialize vehicle database
            VehicleDatabase db = new VehicleDatabase();
            if ("Car".equals(type)) {

                String c = askForCapacity();
                int capacity = Integer.parseInt(c);

                newVehicle = new Car(vehicleID, brand, model, trans, spd, available, dailyRate, capacity, storedImagePath, "Car");

                if (!vehicles.contains(newVehicle)) {
                    vehicles.add(newVehicle);

                    saveVehicleToFile();
                    showVehicleAddedAlert(newVehicle);
                }

            } else if ("Truck".equals(type)) {
                double cargoWeight = Double.parseDouble(askWeights());
                newVehicle = new Truck(vehicleID, brand, model, trans, spd, available, dailyRate, cargoWeight, storedImagePath, "Truck");

                if (!vehicles.contains(newVehicle)) {
                    vehicles.add(newVehicle);

                    saveVehicleToFile();
                    showVehicleAddedAlert(newVehicle);
                }
            }

            else if ("Bike".equals(type)) {
                newVehicle = new Bike(vehicleID, brand, model, trans, spd, available, dailyRate, storedImagePath, "Bike");

                if (!vehicles.contains(newVehicle)) {
                    vehicles.add(newVehicle);

                    saveVehicleToFile();
                    showVehicleAddedAlert(newVehicle);

                }
            }
            vehicleIDField.clear();
            brandField.clear();
            modelField.clear();
            transmission.clear();
            speed.clear();
            dailyRateField1.clear();
            vehicleImageView.setImage(null);
            availabilityCheck.setSelected(false);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to save vehicle: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void saveVehicleToFile() {
        try {
            FileOutputStream file = new FileOutputStream("vehicles.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);

            for (Vehicle vehicle : vehicles) {
                outputFile.writeObject(vehicle);
            }
            outputFile.close();

        } catch(IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed Save Vehicle to database: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void showVehicleAddedAlert(Vehicle vehicle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Vehicle Added ✅");
        alert.setHeaderText("Success!");
        alert.setContentText("The vehicle \"" + vehicle.getBrand() + "\" has been added successfully.");

        alert.getButtonTypes().setAll(ButtonType.OK);

        alert.getDialogPane().setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: #f9f9f9;" +
                        "-fx-text-fill: #2c3e50;"
        );

        alert.showAndWait();
    }
    private String askForCapacity() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Vehicle Capacity Required");
        dialog.setHeaderText("Enter Vehicle Capacity");
        dialog.setContentText("Please enter the seating capacity of the vehicle:");

        dialog.getDialogPane().setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #f9f9f9;"
        );

        Optional<String> result = dialog.showAndWait();

        // If user entered something, return it
        return result.orElse(null);
    }

    private String askWeights() {
        TextInputDialog cargoWeight = new TextInputDialog();

        cargoWeight.setTitle("Truck Weight Required in Tons");
        cargoWeight.setHeaderText("Enter Weight");
        cargoWeight.setContentText("Please enter the Cargo Weight of the Truck:");


        cargoWeight.getDialogPane().setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-background-color: #f9f9f9;"
        );

        Optional<String> result = cargoWeight.showAndWait();

        // If user entered something, return it
        return result.orElse(null);
    }

    public static void recreateVehicleObject(Vehicle vehicle) {
        Vehicle newVehicle;

        if ("Car".equals(vehicle.getType())) {
            Car car = (Car) vehicle;
            String transmission = car.getTransmission();
            transmission = transmission.replace("Transmission: ", "");
            newVehicle = new Car(car.getVehicleID(), car.getBrand(), car.getModel(),
                    transmission, car.getSpeed(), car.getIsAvailable(),
                    car.getDailyRate(), car.getCapacity(),
                    car.getImagePath(), car.getType());

            if (!vehicles.contains(newVehicle)) {
                vehicles.add(newVehicle);
                saveVehicleToFile();
                System.out.println("Returned");

            }
        }
    }
}
