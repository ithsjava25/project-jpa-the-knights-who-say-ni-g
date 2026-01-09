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
import java.math.BigDecimal;

public class MoviePosterController {

    @Inject
    AppModel model;

    @FXML
    private VBox posterSpace;
    @FXML
    private VBox titleSpace;
    @FXML
    private HBox addButtonBox;
    @FXML
    private HBox priceBox;

    Button addMovieButton;
    Movie selectedMovie;

    public void initData(Movie movie) {
        selectedMovie = movie;
        Rectangle poster = new Rectangle(120, 160);
        poster.setArcHeight(15);
        poster.setArcWidth(15);
        poster.setFill(Color.WHITE);
        poster.setStroke(Color.BLACK);
        addMovieButton = new Button("Lägg till");
        Text priceText = new Text(String.format("%.2f kr", movie.getPrice()));

        titleSpace.getChildren().add(new Text(movie.getTitle()));
        posterSpace.getChildren().add(poster);
        addButtonBox.getChildren().add(addMovieButton);
        priceBox.getChildren().add(priceText);
        addListener();
    }

    public void addListener() {
        addMovieButton.setOnAction(e -> {
            System.out.println("Add movie shopping");
            model.addToShoppingCart(selectedMovie);
        });
    }

}
