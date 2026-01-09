package org.example.javafx.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BlockBusterLabelController {

    @FXML
    private BorderPane blockRoot;
    @FXML
    private Label BlockbusterGang;
    @FXML
    private Button homeButton;
    @FXML
    private Button rentalView;
    @FXML
    ScrollPane scrollMain;
    @Inject
    private Instance<Object> instance; //container injection
    @Inject
    NavigationService navigation;

    private final String movieListURL = "/org/example/homescreen.fxml";
    private final String rentalViewURL = "/org/example/rentalview.fxml";

    public void initialize() {
        starter();
    }

    public void starter() {
        homeButton.setOnAction(event -> {
           navigation.setCenter(movieListURL);
        });
        rentalView.setOnAction(event -> {
            navigation.setCenter(rentalViewURL);
        });
    }


    public void setHomeScreen() {
        navigation.setCenter(movieListURL);
    }
}
