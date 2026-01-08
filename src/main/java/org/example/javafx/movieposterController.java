package org.example.javafx;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

public class movieposterController {
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


    public void initData(Movie movie, AppModel model){
    starter();
    }

    public void starter(){
        placeorder.setOnAction(e -> {
            customerService.createCustomer("Anna", "Svensson", "test@mail");
        });

        searchmovietitle.setOnAction(e -> {
            var firstname = customerService.findByEmail("test");
            if(firstname.isPresent()){
                firstname.get().getFirstName();
            }
        });
    }
}
