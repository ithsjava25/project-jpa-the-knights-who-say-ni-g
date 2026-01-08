package org.example.javafx.controller;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.example.service.CustomerService;

public class ChoppingCartController {

    @Inject
    NavigationService navigation;

    @Inject
    CustomerService customerService;

    public Button searchmovietitle;
    public Button placeorder;
    public BorderPane choppingRoot;

    public void initialize(){
        starter();
    }


    public void starter(){
        placeorder.setOnAction(e -> {
            customerService.createCustomer("Anna", "Svensson", "test@mail");
        });
        //TODO Om flere resultat hittas, skiter den it.
        searchmovietitle.setOnAction(e -> {
            var firstname = customerService.findByEmail("test@mail");
            if(firstname.isPresent()){
                firstname.get().getFirstName();
            }else{
                System.out.println("none was found");
            }
        });
    }

}
