package com.example.carrentalsystem;

import com.example.carrentalsystem.backend.Customer;
import com.example.carrentalsystem.backend.CustomerDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AddCustomerController {
    @FXML
    private TextField customerNameField;
    
    @FXML
    private TextField customerIdField;
    
    @FXML
    private TextField customerPhoneField;
    
    @FXML
    private TextField customerAddressField;


    public void saveCustomerBtn(ActionEvent actionEvent) {
        String  customerName = customerNameField.getText();
        String customerId = customerIdField.getText();
        String customerPhone = customerPhoneField.getText();
        String customerAddress = customerAddressField.getText();

        Customer customer = new Customer(customerName, customerId, customerPhone, customerAddress);
        CustomerDatabase customerDatabase = new CustomerDatabase();
        CustomerDatabase.customerList.add(customer);

        try {
            FileOutputStream file = new FileOutputStream("myCustomerList.dat");
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(CustomerDatabase.customerList);
            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
