package com.example.carrentalsystem;

import com.example.carrentalsystem.backend.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> colID;
    @FXML
    private TableColumn<Customer, String> colNames;
    @FXML
    private TableColumn<Customer, String> colPhone;
    @FXML
    private TableColumn<Customer, String> colAddress;

    private static ObservableList<Customer> customerData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Link columns with Customer getters
        colID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colNames.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerData.clear();
        loadCustomers();
    }

    private void loadCustomers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("myCustomerList.dat"))) {
            // Read back the ArrayList
            ArrayList<Customer> list = (ArrayList<Customer>) in.readObject();

            customerData.addAll(list);
            customerTable.setItems(customerData);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int countCustomers() {
        int count = 0;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("myCustomerList.dat"))) {
            ArrayList<Customer> customerList = (ArrayList<Customer>) in.readObject();
            count = customerList.size();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

}

