package org.example.javafx.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import org.example.javafx.AppModel;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

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

    public void initData(Movie movie){
    }



    private void createMovieInfo(Movie movie){
        VBox totalCard = new VBox();
        totalCard.setAlignment(Pos.CENTER);

        VBox titleCard = new VBox();
        titleCard.getChildren().add(new Text(movie.getTitle()));

        VBox blobWindow = new VBox();
        blobWindow.setAlignment(Pos.CENTER);

    }
}
