package org.example;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

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
