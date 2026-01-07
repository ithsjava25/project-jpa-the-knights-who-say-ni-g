package org.example.javafx;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    private SeContainer container;

    @Override
    public void start(Stage primaryStage) {
        try {
            //Start Weld
            container = SeContainerInitializer.newInstance().initialize();
            //Start fxml
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org/example/homescreen.fxml"));
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
    @Override
    public void stop() {
        container.close();
    }


    public static void main (String[] args){
        try {
            launch(args);
        }catch (Exception e){
             e.printStackTrace();
        }
    }


}
