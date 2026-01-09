package org.example.javafx.controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.example.javafx.AppModel;
import org.example.service.CustomerService;
import org.example.service.MovieService;

public class ChoppingCartController {

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
        //starter();
        getShoppingList();
    }

    @PostConstruct
    public void init(){
        System.out.println("Shopping controller laddad av weld!");
        System.out.println("Movie service är laddad " + (movieService != null));
    }

    public void getShoppingList() {
        var shoppingListContent = model.getShoppingCartList();
        if (!shoppingListContent.isEmpty()) {
            for (var item : shoppingListContent) {

                shoppingList.getItems().add(new Text((item.getTitle())));
            }
        }
    }


    public void starter() {


        placeorder.setOnAction(e -> {
            customerService.createCustomer("Anna", "Svensson", "test@mail");
        });
        //TODO Om flere resultat hittas, skiter den it.
        searchmovietitle.setOnAction(e -> {
            var firstname = customerService.findByEmail("test@mail");
            if (firstname.isPresent()) {
                firstname.get().getFirstName();
            } else {
                System.out.println("none was found");
            }
        });
    }

}
