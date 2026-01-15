package org.example.javafx.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @Inject
    NavigationService navigator;

    @FXML private VBox actorBox;
    @FXML private Label titleLabel;
    @FXML private Label priceLabel;
    @FXML private Rectangle posterRectangle;
    @FXML private Button MovieButton;
    @FXML private Button RentMoreMoviesButton;

    private Movie selectedMovie;

    public void initData(Movie movie) {
        this.selectedMovie = movie;

        // Uppdaterar innehållet dynamiskt
       titleLabel.setText(movie.getTitle());
       priceLabel.setText(String.format("%.2f", movie.getPrice()));

        // PLaceholder för framtida bilder
        // Image image = new Image(movie.getImageUrl());
        //posterRectangle.setFill(new ImagePattern(img));

        showActors(movie);
        addListener();
    }

    public void addListener() {
        // Lägger till film i kundvagnen
        MovieButton.setOnAction(e -> {
            System.out.println("Add movie shopping");
            model.addToShoppingCart(selectedMovie);
        });

        // Knapp --> Gå tillbaka till startsidan/Homescreen
        RentMoreMoviesButton.setOnAction(e -> {
            System.out.println("Navigerar tillbaka till startsidan...");
            navigator.setCenter("/org/example/homescreen.fxml");

        });
    }
    private void showActors(Movie movie) {
        actorBox.getChildren().clear();

        if (movie.getActor() == null || movie.getActor().isEmpty()) {
            return;
        }

        Label header = new Label("Skådespelare:");
        header.getStyleClass().add("actor-header");
        actorBox.getChildren().add(header);

        movie.getActor().forEach(actor -> {
            Label actorLabel = new Label(
                actor.getFirstName() + " " + actor.getLastName()
            );
            actorLabel.getStyleClass().add("actor-name");
            actorBox.getChildren().add(actorLabel);
        });
    }

}
