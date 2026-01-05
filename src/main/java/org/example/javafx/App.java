package org.example.javafx;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.example.service.RentalService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

import java.util.ArrayList;
import java.util.List;

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

            //persistanceStart();
        }catch (Exception e){
            System.out.println("Error with hibernate start: " + e.getMessage());
        }
        try {
            launch(args);
        }catch (Exception e){
             e.printStackTrace();
        }
    }
    private static void persistanceStart() {
        List<Class<?>> entities = getClasses("org.example.tables");
        System.out.println(entities.size());

        final PersistenceConfiguration cfg = new HibernatePersistenceConfiguration("emf")
            .jdbcUrl("jdbc:mysql://localhost:3306/blockbuster_gang")
            .jdbcUsername("root")
            .jdbcPassword("root")
            .property("hibernate.hbm2ddl.auto", "update")
            .property("hibernate.show_sql", "true")
            .property("hibernate.format_sql", "true")
            .property("hibernate.highlight_sql", "true")
            .managedClasses(entities);

        try (EntityManagerFactory emf = cfg.createEntityManagerFactory()){
//            emf.runInTransaction(
//                entityManager -> {
//                    Customer customer = new Customer();
//
//                }
//            );
        }


//        RentalService rs = new RentalService();
//        Customer kund = new Customer("bob","bobby","Bobsson");
//        Movie movie1 = new Movie();
//        Movie movie2 = new Movie();
//        Movie movie3 = new Movie();
//        List<Movie> test = List.of(movie1, movie2, movie3);
//
//        rs.rentMovies(kund, test);

    }

    private static List<Class<?>> getClasses(String pkg) {
        List<Class<?>> entities;
        try (ScanResult scanResult =
                 new ClassGraph()
                     .verbose()
                     .enableAllInfo()
                     .acceptPackages(pkg)
                     .scan()) {
            entities = scanResult.getClassesWithAnnotation(Entity.class).loadClasses();
        }
        return entities;
    }
}
