package org.example;

import org.example.javafx.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class AppIT {

    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.36")
    .withDatabaseName("test_db")
    .withUsername("username")
    .withPassword("password");

    @BeforeAll
    static void wireDbProperties() {
        System.out.println("Container JDBC URL: " + mysql.getJdbcUrl());
        System.setProperty("APP_DB_URL", mysql.getJdbcUrl());
        System.setProperty("APP_DB_USER", mysql.getUsername());
        System.setProperty("APP_DB_PASS", mysql.getPassword());

    }

    @Test
    void TestHibernateConnection() {
        System.out.println("Försöker hämta SessionFactory");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory hämtad!");

        try (Session session = sessionFactory.openSession()) {
            assertThat(session.isConnected()).isTrue();

            // Funkar men raden nedan (Object result) är bättre praxis
//            Long result = (Long) session.createNativeQuery("SELECT 1").getSingleResult();
//            assertThat(result).isEqualTo(1);

            Object result = session.createNativeQuery("SELECT 1").getSingleResult();
            assertThat(result.toString()).isEqualTo("1");

        }
    }
}
