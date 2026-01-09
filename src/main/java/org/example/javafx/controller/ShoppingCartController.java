package org.example.javafx.controller;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.javafx.AppModel;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

public class ShoppingCartController {

    @Inject
    NavigationService navigation;
    @Inject
    AppModel model;
    @Inject
    RentalService rentalService;

    @FXML
    private ListView shoppingList;
    @FXML
    private Button removeMovie;
    @FXML
    private Button rentMovies;

    private final String rentalView = "/org/example/rentalview.fxml";

    public void initialize() {
        fixListViewFactory();
        shoppingList.setItems(model.getShoppingCartList());
        addListener();
        removeMovie.disableProperty().bind(shoppingList.getSelectionModel().selectedItemProperty().isNull());
        rentMovies.disableProperty().bind(Bindings.isEmpty(model.getShoppingCartList()));
    }

    @PostConstruct
    public void init(){
    }

    public void getShoppingList() {
        var shoppingListContent = model.getShoppingCartList();
        if (!shoppingListContent.isEmpty()) {
            for (var item : shoppingListContent) {

                shoppingList.getItems().add(new Text((item.getTitle())));
            }
        }
    }


    public void fixListViewFactory() {
        shoppingList.setCellFactory(lv -> new ListCell<Movie>(){
            @Override
            protected void updateItem(Movie item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }else{
                    setText(item.getTitle());
                }
            }
        });
    }
    public void addListener() {
        rentMovies.setOnAction(e -> {
            rentalService.rentMovies(model.getLoggedCustomer(), model.getShoppingCartList());
            model.clearShoppingCart();
            navigation.setCenter(rentalView);
        });

        removeMovie.setOnAction(e -> {
           //find out a way to mark a movie and remove from cart.
            Movie selectedMovie = (Movie) shoppingList.getSelectionModel().getSelectedItem();
            if (selectedMovie != null){
                model.removeFromShoppingCart(selectedMovie);
            }
        });
    }

}
