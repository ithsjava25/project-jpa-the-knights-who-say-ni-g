package org.example.javafx.controller;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.example.javafx.AppModel;
import org.example.repository.CustomerRepository;
import org.example.repository.MovieRepository;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Customer;
import org.example.tables.Movie;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RentalViewController {

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
    TableView<Movie> rentalTable;

    @FXML
    TableColumn titleColumn;

    @FXML
    TableColumn priceColumn;

    @FXML
    Label totalPriceLabel;

    @FXML
    BorderPane rentalRoot;

    @FXML
    Button logOut;
    @Inject
    private CustomerService customerService;

    @FXML
    public void initialize() {

        //Kopplar text till
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

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
            List<Movie> rentedMovies = rentalService.getRentedMoviesByCustomer(currentCustomer);

            //Lägger till listan i TableView
            rentalTable.setItems(FXCollections.observableArrayList(rentedMovies));

            //Uppdaterar totalpriset av alla filmer och syns som text
            BigDecimal totalPrice = rentalService.calculatePrice(rentedMovies);
            totalPriceLabel.setText(totalPrice.toString() + "kr");
        } catch (Exception e) {
            System.err.println("Kunde inte visa några filmer: " + e.getMessage());
        }


    }

//    // ===== Test‑support =====
//    /**
//     * Skapar eller hämtar en testcustomer och hyr ut ett antal filmer till den.
//     * Används för att fylla tabellen vid utveckling/testning.
//     */
//    private Optional<Customer> createOrFetchTestCustomer() {
//        // Förutsatt att du har en CustomerRepository tillgänglig via DI
//        Optional<Customer> testCustomer = customerService.findByEmail("anna.andersson@test.se");
//        if (!testCustomer.isPresent()) {
//            //Optional<Customer> newCustomer = Optional.of(new Customer("Test", "user", "test@mail"));
//            // Spara kunden – antag att du har en save‑metod
//            customerService.createCustomer("Test", "user", "test@mail");
//        }
//        return testCustomer;
//    }
//
//    /**
//     * Håller koll på vilka filmer som ska hyras ut i test.
//     */
//    private List<Movie> getTestMovies() {
//        // Exempel: hämta de första 3 filmerna från MovieRepository
//        return movieService.getAllMovies().stream()
//            .limit(3)
//            .collect(Collectors.toList());
//    }
//
//    /**
//     * Laddar hyrda filmer för test‑kunden.
//     */
//    private void loadTestRentedMovies() {
//        Optional<Customer> testCustomer = createOrFetchTestCustomer();
//        List<Movie> moviesToRent = getTestMovies();
//
//        // Om kunden ännu inte har hyrt någon film, skapa en ny uthyrning
//        if (rentalService.getRentedMoviesByCustomer(testCustomer.get()).isEmpty()) {
//            rentalService.rentMovies(testCustomer.get(), moviesToRent);
//        }
//
//        List<Movie> rented = rentalService.getRentedMoviesByCustomer(testCustomer.get());
//        // Här kan du sedan binda 'rented' till din TableView eller liknande
//        rentalTable.setItems(FXCollections.observableArrayList(rented));
//        BigDecimal totalPrice = rentalService.calculatePrice(rented);
//        totalPriceLabel.setText(totalPrice.toString() + "kr");
//    }


}
