package org.example.javafx.controller;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.example.javafx.AppModel;
import org.example.service.CustomerService;
import org.example.tables.Customer;

import java.awt.*;

public class LoginViewController {


    public BorderPane loginRoot;
    public TextField forNamn;
    public TextField efterNamn;
    public TextField eMail;
    public Button logInReg;
    public Label errorLabel;
    @Inject
    NavigationService navigator;
    @Inject
    CustomerService customerService;
    @Inject
    AppModel model;



    @FXML
    public void initialize(){
        handleLogIn();
    }

    public void handleLogIn() {

        if (forNamn.getText().isBlank()
            || efterNamn.getText().isBlank()
            || eMail.getText().isBlank()) {

            errorLabel.setText("Alla fält måste fyllas i");
            errorLabel.setVisible(true);
            return;
        }

        Customer customer = customerService.logInOrRegister(
            forNamn.getText(),
            efterNamn.getText(),
            eMail.getText()
        );

        model.setLoggedCustomer(customer);
        navigator.setCenter("/org/example/homescreen.fxml");
    }

}
