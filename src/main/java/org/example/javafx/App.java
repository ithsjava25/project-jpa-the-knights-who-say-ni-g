package org.example.javafx;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.service.RentalService;

import java.util.Timer;
import java.util.TimerTask;


public class App extends Application{

    private SeContainer container;
    private Timer timer;

    @Override
    public void start(Stage primaryStage) {
        try {

            //Start Weld
            container = SeContainerInitializer.newInstance().initialize();

            // Gets RentalService from container, new instance is created by Weld
            RentalService rentalService = container.select(RentalService.class).get();

            // Clears expired RentalMovies from database, set intervall
            timer = new Timer(true); // daemon-timer

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        rentalService.cleanupRentals();
                        System.out.println("Timer cleanup executed");
                    } catch (Exception e) {
                        System.err.println("Timer cleanup failed: " + e.getMessage());
                    }
                }
                }, 0,          // kör direkt vid start
                20 * 1000   // kör var 20:e sekund
            );

            //Start fxml
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org/example/maincontrollerview.fxml"));
            //Connect weld with fxml
            fxmlLoader.setControllerFactory(type -> container.select(type).get());


            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 720, 480);

            primaryStage.setTitle("Blockbuster");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            System.out.println("Error from start app overide!");
            e.printStackTrace();

            if(e.getCause() != null){
                System.err.println("------Cause------");
                e.getCause().printStackTrace();
            }
        }

    }


    public SeContainer getContainer() {
        return container;
    }

    @Override
    public void stop() {
        if (timer!=null) {
            timer.cancel();
        }
        if (container!=null) {
            container.close();
        }
    }

//Hickaricp, använder en pool av connections till databasen
    public static void main (String[] args){
        try {
            launch(args);
        }catch (Exception e){
             e.printStackTrace();
        }
    }


}
