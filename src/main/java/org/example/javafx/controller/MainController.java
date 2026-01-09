package org.example.javafx.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.javafx.AppModel;
import org.example.tables.Movie;

//All fxml controller has Dependent as default but invisible. here is only for example
@Dependent
public class MainController {

    @FXML
    private BorderPane root;
    @FXML
    private VBox centerVbox;
    @FXML
    private VBox leftVbox;
    @FXML
    private HBox topHbox;

    //CDI if new classes needs to be made, Controller factory must be made from this
    @Inject
    private Instance<Object> instance;
    @Inject
    private AppModel model;

    //If a class needs contact with MainController, use navigation instead.
    @Inject
    NavigationService navigationService;

    @FXML
    public void initialize(){
        navigationService.setMainController(this);
        setTop("/org/example/blockbusterlabel.fxml");
        setCenter("/org/example/homescreen.fxml");

    }

    public void emptyCenter(){
        root.setLeft(null);
        root.setCenter(null);
    }
    public void setCenter(String fxml){
        emptyCenter();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(type -> instance.select(type).get()); //Without this beans wont inject properly to fxml controller
            Parent view = loader.load();
            root.setCenter(view);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setCenter(String fxml, Movie movie){
        emptyCenter();
        model.testStart();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(type -> instance.select(type).get());
            Parent view = loader.load();
            MoviePosterController controller = loader.getController();
            root.setCenter(view);
            controller.initData(movie);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setTop(String fxml){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(type -> instance.select(type).get());
            Parent view = loader.load();
            root.setTop(view);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setLeft(String fxml){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(type -> instance.select(type).get());
            Parent view = loader.load();
            root.setLeft(view);
        }catch(Exception e){
            e.printStackTrace();
        }
    }




}
