package org.example.javafx;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory sf = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("Hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("dialect", "org.hibernate.dialect.MySQLDialect")
                .applySetting("hibernate.connection.url",
                    System.getenv("DB_URL"))
                .applySetting("hibernate.connection.username", System.getenv("DB_USERNAME"))
                .applySetting("hibernate.connection.password", System.getenv("DB_PASSWORD"))
                .applySetting("hibernate.current_session_context_class", "thread")
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.transaction.coordinator_class", "jta")
                .applySetting("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.JBossStandAloneJtaPlatform")
                .build();

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
        }catch (Exception e){
            System.err.println("INITIALIZATION ERROR HibernateUtil: ");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }


    public static SessionFactory getSessionFactory(){
        return sf;
    }
}
