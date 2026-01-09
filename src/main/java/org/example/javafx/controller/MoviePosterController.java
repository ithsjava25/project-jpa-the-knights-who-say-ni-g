package org.example.javafx.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import org.example.javafx.AppModel;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

import javax.smartcardio.Card;

public class MoviePosterController {
    @Inject
    CustomerService customerService;
    @Inject
    MovieService movieService;
    @Inject
    RentalService rentalService;
    @Inject
    private Instance<Object> instance; //container injection

    @FXML
    Button placeorder;
    @FXML
    Button searchmovietitle;
    @FXML
    private BorderPane movieRoot;
    @FXML
    private VBox baseVbox;
    @FXML
    private VBox moviePoster;
    @FXML
    private VBox posterSpace;
    @FXML
    private VBox titleSpace;
    @FXML
    private HBox buttonSpace;
    @FXML
    private HBox addButtonBox;
    @FXML
    private HBox priceBox;

    Button addMovieButton;

    public void initData(Movie movie) {
        System.out.println("initdata movie:" + movie.getTitle());
        Rectangle poster = new Rectangle(120, 160);
        poster.setArcHeight(15);
        poster.setArcWidth(15);
        poster.setFill(Color.WHITE);
        poster.setStroke(Color.BLACK);
        addMovieButton = new Button("Lägg till");
        Text priceText = new Text("299kr");

        titleSpace.getChildren().add(new Text(movie.getTitle()));
        posterSpace.getChildren().add(poster);
        addButtonBox.getChildren().add(addMovieButton);
        priceBox.getChildren().add(priceText);
        buttonSpace.getChildren().addAll(addButtonBox, priceBox);
    }


    private void createMovieInfo(Movie movie) {


    }
}
