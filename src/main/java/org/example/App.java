package org.example;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.example.service.RentalService;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
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
