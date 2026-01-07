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
                .applySetting("hibernate.transaction.coordinator_class", "jta")
                .applySetting("hibernate.transaction.jta.platform", "org.hibernate.engine.transaction.jta.platform.internal.JBossStandAloneJtaPlatform")
                .build();

            return new MetadataSources(registry)
                .addAnnotatedClasses(org.example.tables.Movie.class)
                .addAnnotatedClasses(org.example.tables.Rental.class)
                .addAnnotatedClasses(org.example.tables.Actor.class)
                .addAnnotatedClasses(org.example.tables.Customer.class)
                .getMetadataBuilder()
                .build()
                .getSessionFactoryBuilder()
                .build();
        }catch (Exception e){
            System.err.println("INITIALZATION ERROR HibernateUtil: ");
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }


    public static SessionFactory getSessionFactory(){
        return sf;
    }
}
