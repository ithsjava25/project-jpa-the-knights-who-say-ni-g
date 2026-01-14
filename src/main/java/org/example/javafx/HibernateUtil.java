package org.example.javafx;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory sf = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try {
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .applySetting("hibernate.connection.url", System.getProperty("APP_DB_URL", System.getenv("DB_URL")))
                .applySetting("hibernate.connection.username", System.getProperty("APP_DB_USER", System.getenv("DB_USERNAME")))
                .applySetting("hibernate.connection.password", System.getProperty("APP_DB_PASS", System.getenv("DB_PASSWORD")))
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.show_sql", "true");

            //För att undvika krock, aktivera endast JTA om vi INTE kör ett test(dv. APP_DB_URL saknas)
            if (System.getProperty("APP_DB_URL") == null) {
                builder.applySetting("hibernate.transaction.coordinator_class", "jta")
                    .applySetting("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.JBossStandAloneJtaPlatform");
            }

            StandardServiceRegistry registry = builder.build();

            return new MetadataSources(registry)
                .addAnnotatedClasses(org.example.tables.Movie.class)
                .addAnnotatedClasses(org.example.tables.Rental.class)
                .addAnnotatedClasses(org.example.tables.Actor.class)
                .addAnnotatedClasses(org.example.tables.Customer.class)
                .addAnnotatedClasses(org.example.tables.RentalMovie.class)
                .getMetadataBuilder()
                .build()
                .getSessionFactoryBuilder()
                .build();
        } catch (Exception e) {
            System.err.println("INITIALIZATION ERROR HibernateUtil: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }


    public static SessionFactory getSessionFactory(){
        return sf;
    }
}
