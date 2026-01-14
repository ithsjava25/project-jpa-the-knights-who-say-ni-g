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
    public Label loginViewHeader;
    @Inject
    NavigationService navigator;
    @Inject
    CustomerService customerService;
    @Inject
    AppModel model;


    @FXML
    public void initialize() {
        logInReg.setOnAction(event -> handleLogIn());
    }

    public void handleLogIn() {

        if (forNamn.getText().isBlank()
            || efterNamn.getText().isBlank()
            || eMail.getText().isBlank()) {

            errorLabel.setText("Alla fält måste fyllas i");
            errorLabel.setVisible(true);
            return;
        }
        try {
            Customer customer = customerService.logInOrRegister(
                forNamn.getText(),
                efterNamn.getText(),
                eMail.getText()
            );

            if (customer == null) {
                errorLabel.setText("Inloggning misslyckades");
                errorLabel.setVisible(true);
                return;
            }

            model.setLoggedCustomer(customer);
            navigator.setCenter("/org/example/homescreen.fxml");
        } catch (Exception e) {
            errorLabel.setText("Ett fel uppstod: " + e.getMessage());
            errorLabel.setVisible(true);
        }
    }

}
