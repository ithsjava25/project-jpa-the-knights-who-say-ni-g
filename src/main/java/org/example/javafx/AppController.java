package org.example.javafx;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.example.repository.MovieRepository;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.hibernate.StatelessSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppController {

    @Inject
    CustomerService customerService;
    @Inject
    MovieService movieService;
    @Inject
    RentalService rentalService;

    @FXML
    BorderPane root;
    @FXML
    Button searchmovietitle;
    @FXML
    Button placeorder;

    private final AppModel model = new AppModel();


    @FXML
    public void initialize(){

        printOutButtons();
    }

    public void printOutButtons(){
        StatelessSession session = HibernateUtil.getSessionFactory().openStatelessSession();
        System.out.println("New customer! ");
        session.beginTransaction();
        Customer customer = new Customer("Bob", "bobby", "bobatsson");
        session.insert(customer);
        session.getTransaction().commit();
        searchmovietitle.setOnAction(e -> {
            System.out.println("Added test moive ");
            session.beginTransaction();
            Movie movie = new Movie("test", "action", 200, BigDecimal.TEN, 5, 10);


            session.insert(movie);
            session.getTransaction().commit();
            System.out.println("done!");
        });

        placeorder.setOnAction(e -> {
            System.out.println("Add customer anna anderson");
            customerService.createCustomer("Anna", "Andersson", "test");

        });
    }
}
