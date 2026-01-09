package org.example.javafx.controller;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.tables.Movie;

@ApplicationScoped
public class NavigationService {

    private MainController mc;

    public void setMainController(MainController mc){
        this.mc=mc;
    }

    public void setCenter(String fxml){
        mc.setCenter(fxml);
    }
    public void setCenter(String fxml, Movie movie){
        mc.setCenter(fxml, movie);
    }
    public void setLeft(String fxml){
        mc.setLeft(fxml);
    }


}
