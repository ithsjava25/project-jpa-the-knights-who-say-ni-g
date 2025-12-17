package org.example.javafx;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.tables.Customer;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

import java.util.List;

public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/org/example/homescreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 720, 480);
        primaryStage.setTitle("Blockbuster");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main (String[] args){
//        try {
//            persistanceStart();
//        }catch (Exception e){
//            System.out.println("Error with hibernate start: " + e.getMessage());
//        }
        try {
            launch();
        }catch (Exception e){
            System.out.println("Error running launch: " + e.getMessage() + " stack: " + e.getStackTrace());
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


        try (EntityManagerFactory emf = cfg.createEntityManagerFactory()) {
            emf.runInTransaction(
                entityManager -> {
                    Customer customer = new Customer();

                }
            );
        }
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






