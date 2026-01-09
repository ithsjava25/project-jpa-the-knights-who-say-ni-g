package org.example.javafx.controller;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.beans.property.SimpleObjectProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.example.repository.MovieRepository;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

import java.util.List;

public class RentalViewController {

    @Inject
    private RentalService rentalService;

    @Inject
    private MovieService movieService;

    @Inject
    private Instance<Object> instance; //container injection
    @FXML
    TableView<Movie> rentalTable;

    @FXML
    TableColumn titleColumn;

    @FXML
    TableColumn priceColumn;

    @FXML
    Label totalPriceLabel;

    @FXML
    BorderPane root;

    @FXML
    Button homeButton;

    @FXML
    public void initialize() {

    }

    private void loadRentedMovies() {
        /*
        List<MovieRepository> rentedMovies = MovieService.//lista av filmer
        rentalTable.setItems(FXCollections.observableArrayList(rentedMovies));

         */
    }
}
