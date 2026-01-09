package org.example.javafx.controller;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.javafx.AppModel;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.tables.Movie;

public class ShoppingCartController {

    @Inject
    NavigationService navigation;
    @Inject
    AppModel model;
    @Inject
    CustomerService customerService;
    @Inject
    MovieService movieService;

    public Button searchmovietitle;
    public Button placeorder;
    public BorderPane choppingRoot;
    @FXML
    private ListView shoppingList;

    public void initialize() {
        fixListViewFactory();
        shoppingList.setItems(model.getShoppingCartList());
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

}
