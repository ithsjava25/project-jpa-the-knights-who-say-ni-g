package org.example.javafx.controller;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.example.javafx.AppModel;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.example.tables.RentalMovie;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RentalViewController {


    public Label extraPriceLabel;
    @Inject
    NavigationService navigator;

    @Inject
    private RentalService rentalService;

    @Inject
    private MovieService movieService;

    @Inject
    private Instance<Object> instance; //container injection

    @Inject
    AppModel model;

    @FXML
    TableView<RentedMovieView> rentalTable; // todo: lösa så att tableview kan se rentaltabellen

    @FXML
    TableColumn<RentedMovieView, Void> renewColumn;

    @FXML
    TableColumn<RentedMovieView, LocalDateTime> returnColumn;

    @FXML
    TableColumn<RentedMovieView, String> titleColumn;

    @FXML
    TableColumn<RentedMovieView, BigDecimal> priceColumn;

    @FXML
    Label totalPriceLabel;

    @FXML
    BorderPane rentalRoot;

    @FXML
    Button logOut;
    @Inject
    private CustomerService customerService;

    private static final DateTimeFormatter returnDateTimeFormat =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");



    @FXML
    public void initialize() {

        //Kopplar text till
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        returnColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        renewColumn.setCellFactory(col-> new TableCell<>(){
            private final Button renewButton = new Button("Hyr om");
            {
                renewButton.setOnAction(event -> {
                    RentedMovieView rental =
                        getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(
                        Alert.AlertType.CONFIRMATION,
                        "Förläng uthyrningen med 24h för 29kr?",
                        ButtonType.YES,
                        ButtonType.NO
                    );
                    if(alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES){
                        //Raden man klickar på förlängs
                        rentalService.renewRentalMovie(rental.getRentalId());
                        loadRentedMovies();
                    }

                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){
                    setGraphic(null);
                } else {
                    setGraphic(renewButton);
                }
            }
        });

        returnColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(returnDateTimeFormat));
                }
            }
        });



        logOut.setOnAction(event -> {
            navigator.setCenter("/org/example/loginview.fxml");
            //Platform.exit();
        });

        loadRentedMovies();

    }

    private void loadRentedMovies() {

        try{
            Customer currentCustomer = model.getLoggedCustomer();

            //Anropar listan som skapats i Rentalservice via Native Query
            List<RentedMovieView> rentedMovies = rentalService.getRentedMoviesByCustomer(currentCustomer);

            //Lägger till listan i TableView
            rentalTable.setItems(FXCollections.observableArrayList(rentedMovies));

            //Visar totalt pris för uthyrning - hämtas via RentedMovieView
            BigDecimal totalRentalPrice = rentedMovies.stream()
                .map(RentedMovieView::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalExtraCost = rentedMovies.stream()
                .map(RentedMovieView::getAdditionalCost)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            //Uppdaterar totalpriset av alla filmer och syns som text
            totalPriceLabel.setText(totalRentalPrice.toString() + "kr");
            //Uppdaterar label för extra kostnader
            extraPriceLabel.setText(totalExtraCost + "kr");

        } catch (Exception e) {
            System.err.println("Kunde inte visa några filmer: " + e.getMessage());
        }


    }



}
