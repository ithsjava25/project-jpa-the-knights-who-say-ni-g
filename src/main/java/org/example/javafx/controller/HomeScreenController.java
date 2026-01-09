package org.example.javafx.controller;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

import java.util.List;
public class HomeScreenController {

    @Inject
    CustomerService customerService;
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
        getAllMovies();
//        topListener();
        //printOutButtons();
    }
    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void createMoviePosters(Movie movie) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setSpacing(5);

        Rectangle poster = new Rectangle(120, 160);
        poster.setArcHeight(15);
        poster.setArcWidth(15);
        poster.setFill(Color.WHITE);
        poster.setStroke(Color.BLACK);

        Label titleLabel = new Label(movie.getTitle());
        titleLabel.setMaxWidth((115));
        titleLabel.setWrapText(false);
        titleLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
        titleLabel.setAlignment(Pos.CENTER);
        card.getChildren().addAll(poster, titleLabel);
        topMoviecard.setSpacing(10);
        topMoviecard.getChildren().add(card);
        scrollTopPane.setContent(topMoviecard);
        card.setOnMouseClicked(event -> handleMovieClick(movie));


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

    public void getAllMovies(){
        List<Movie> allMovies = movieService.getAllMovies();

        for(Movie movie : allMovies){
            createMoviePosters(movie);
        }
    }

}

