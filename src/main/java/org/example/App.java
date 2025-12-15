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
        List<Class<?>> entites;

        try(ScanResult scanResult =
                new ClassGraph()
                        .verbose()
                        .enableAllInfo()
                        .acceptPackages("org.example.tables")
                        .scan()) {
            entites = scanResult.getClassesWithAnnotation(Entity.class).loadClasses();
        }

        final PersistenceConfiguration cfg = new HibernatePersistenceConfiguration("enf")
            .jdbcUrl("jdbc:mysql://localhost:3306/blockbuster_gang")
            .jdbcUsername("root")
            .jdbcPassword("root")
            .property("hibernate.hbm2ddl.auto", "update")
            .property("hibernate.show_sql", "true")
            .property("hibernate.format_sql", "true")
            .property("hibernate.highlight_sql", "true")
            .managedClasses(entites);
        try (EntityManagerFactory emf = cfg.createEntityManagerFactory()){


        }
    }
}
