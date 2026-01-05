package org.example.javafx;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.persistence.Entity;
import org.example.tables.Actor;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HibernateUtil {

    private static final SessionFactory sf = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        List<Class<?>> entities = getClasses("org.example.tables");
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("dialect", "org.hibernate.dialect.MySQL8Dialect")
                .applySetting("hibernate.connection.url",
                    "jdbc:mysql://localhost:3306/blockbuster_gang")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "root")
                .applySetting("hibernate.current_session_context_class", "thread")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .build();

            Metadata metadata =
                new MetadataSources(registry)
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Movie.class)
                .addAnnotatedClass(Rental.class)
                .buildMetadata();

            return metadata.buildSessionFactory();
        }catch (Exception e){
            throw new ExceptionInInitializerError(e);
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

    public static SessionFactory getSessionFactory(){
        return sf;
    }
}
