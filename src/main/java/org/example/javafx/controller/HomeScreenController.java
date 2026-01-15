package org.example.javafx.controller;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.tables.Actor_.movie;
import static org.example.tables.Movie_.genre;

public class HomeScreenController {

    @Inject
    CustomerService customerService;
    @Inject
    SearchService searchService;
    @Inject
    MovieService movieService;
    @Inject
    RentalService rentalService;
    @Inject
    NavigationService navigation;

    private final String moviePosterURL = "/org/example/movieposter.fxml";
    private final String shoppingCartURL = "/org/example/shoppingcart.fxml";


    @FXML
    BorderPane root;
    @FXML
    ScrollPane scrollTopPane;
    @FXML
    ScrollPane scrollLowPane;
    @FXML
    HBox topMoviecard;
    @FXML
    HBox lowMoviecard;


    @FXML
    public void initialize() {
        // Initial loading
        refreshMovieDisplay("");

        refreshGenreDisplay();

        searchService.searchQueryProperty().addListener((observable, oldValue, newValue) -> {
            refreshMovieDisplay(newValue);
        });


    }
    public void setRoot(BorderPane root) {
        this.root = root;
    }

    private void refreshMovieDisplay(String searchText) {
        topMoviecard.getChildren().clear();

        // Gets filtered movie list based on search input
        List<Movie> filteredMovies = movieService.searchMovies(searchText);

        if (filteredMovies.isEmpty()) {
            System.out.println("No movies found" + searchText);
        } else {
            // Creates new movie posters based on new list
            for (Movie movie : filteredMovies) {
                createMoviePosters(movie);
                System.out.println("Film: " + movie.getTitle() + " Genre: " + movie.getGenre());
            }
        }

    }

    public void createMoviePosters(Movie movie) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setSpacing(5);

        Rectangle poster = new Rectangle(120, 160);
        poster.setArcHeight(15);
        poster.setArcWidth(15);
        //poster.setFill(Color.WHITE);
        poster.setStroke(Color.BLACK);

        var resource = getClass().getResource("/images/" + "default.jpg");

        if (resource != null) {
            // 2. Skapa bilden (utan 'true' för att säkerställa att den laddas direkt)
            Image img = new Image(resource.toExternalForm());
            // 3. Fyll rektangeln med bilden
            poster.setFill(new ImagePattern(img));
        } else {
            // Om bilden saknas, använd en snygg reservfärg (t.ex. mörkgrå)
            poster.setFill(Color.web("#2a2a2a"));
            System.out.println("Kunde inte hitta bild: " + movie.getImageUrl());
        }

        Label titleLabel = new Label(movie.getTitle());
        titleLabel.setMaxWidth((115));
        titleLabel.setWrapText(false);
        titleLabel.setTextFill(Color.BLACK);
        titleLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        titleLabel.setAlignment(Pos.CENTER);

        // Adds genre to MoviePosters, can be modified later!
        Label genreLabel = new Label(movie.getGenre());
        genreLabel.setMaxWidth((115));
        genreLabel.setWrapText(false);
        genreLabel.setTextFill(Color.BLACK);
        genreLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        genreLabel.setAlignment(Pos.CENTER);

        card.getChildren().addAll(poster, titleLabel,genreLabel);

        topMoviecard.setSpacing(10);
        topMoviecard.getChildren().add(card);
        scrollTopPane.setContent(topMoviecard);
        card.setOnMouseClicked(event -> handleMovieClick(movie));
    }

    public void refreshGenreDisplay() {
        lowMoviecard.getChildren().clear();

        // Gets all movies
        List<Movie> allMovies = movieService.getAllMovies();

        // Extracts all unique genres+ filter away null-values
        Set<String> uniqueGenres = allMovies.stream()
            .map(Movie::getGenre)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        // Creates unique card for every genre
        for (String genre : uniqueGenres) {
            createGenreCard(genre);
        }
    }

    public void createGenreCard(String genre) {
        StackPane cardStack = new StackPane();

        Rectangle genreCard = new Rectangle(120, 120);
        genreCard.setArcHeight(15);
        genreCard.setArcWidth(15);
        genreCard.setFill(Color.WHITE);
        genreCard.setStroke(Color.BLACK);

        Label genreLabel = new Label(genre.toUpperCase());
        genreLabel.setStyle("-fx-font-weight: bold");
        genreLabel.setWrapText(true);
        genreLabel.setTextFill(Color.BLACK);
        genreLabel.setMaxWidth((115));
        genreLabel.setAlignment(Pos.CENTER);

        cardStack.getChildren().addAll(genreCard, genreLabel);

        cardStack.setOnMouseClicked(event -> {
            searchService.setSearchQuery(genre);
            refreshMovieDisplay(genre);
        });

        lowMoviecard.setSpacing(10);
        lowMoviecard.getChildren().add(cardStack);
    }

    private void handleMovieClick(Movie movie) {
        navigation.setCenter(moviePosterURL, movie);
        navigation.setLeft(shoppingCartURL);

    }
//    public void topListener(){
//        homeButton.setOnMouseClicked(event -> {
//            changeToHomescreen();
//        });
//
//    }

}

