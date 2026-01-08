package org.example.javafx;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
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

    private void createMovieInfo(Movie movie){
        VBox totalCard = new VBox();
        totalCard.setAlignment(Pos.CENTER);

        VBox titleCard = new VBox();
        titleCard.getChildren().add(new Text(movie.getTitle()));

        VBox blobWindow = new VBox();
        blobWindow.setAlignment(Pos.CENTER);

    }
}
