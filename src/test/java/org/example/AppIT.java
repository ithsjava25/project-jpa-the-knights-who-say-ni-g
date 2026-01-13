package org.example;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.io.SessionInputBuffer;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.javafx.HibernateUtil;
import org.example.repository.CustomerRepository;
import org.example.repository.CustomerRepository_;
import org.example.repository.MovieRepository;
import org.example.repository.MovieRepository_;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.hibernate.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        // Flytta SeContainer till @BeforeAll för att minska påverkan på prestanda?

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

    // Skapa användare/Customer - C
    @Test
    void testCreateCustomer() {
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Transaction tx = ss.beginTransaction();

            CustomerRepository customerRepository = new CustomerRepository_(ss);

            Customer customer = new Customer();
            customer.setFirstName("Anna");
            customer.setLastName("Andersson");
            customer.setEmail("test@gmail.com");

            customerRepository.save(customer);
            tx.commit();

            assertThat(customer.getCustomerId()).isNotNull();
        }
    }

    // Skapa uthyrning/Rental
    @Test
    void testCreateRental() {
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Transaction tx = ss.beginTransaction();
            try {
                // Skapar test-kund och test-film och sparar
            Customer customer = new Customer("Förnamn", "Efternamn", "test@mail.com");
            ss.insert(customer);

            Movie movie = new Movie("Interstellar", "Sci-Fi", new BigDecimal("99"));
            ss.insert(movie);

                // Create rental-objekt
            LocalDateTime now = LocalDateTime.now();
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(now);
            rental.setReturnDate(now.plusHours(24));
            rental.setTotalRentalPrice(new BigDecimal("50.0"));
            // Sparar rental-object
            ss.insert(rental);

            // Skapar kopplingstabellen manuellt
                // --> För att få in data i kopplingstabellen när vi använder Stateless Session
            ss.createNativeQuery(
                    "INSERT INTO movie_rental (rental_id, movie_id) VALUES (:rId, :mId)")
                .setParameter("rId", rental.getRentalId())
                .setParameter("mId", movie.getId())
                .executeUpdate();

            tx.commit();

            // Verifiering
            assertThat(rental.getRentalId()).isNotNull();
            // Kontrollerar kopplingen
            Object count = ss.createNativeQuery("SELECT COUNT(*) FROM movie_rental WHERE rental_id = :rId")
                .setParameter("rId", rental.getRentalId())
                .getSingleResult();

            assertThat(((Number) count).intValue()).isEqualTo(1);


        } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
    }

    // räkna total price
    @Test
    void testCalculateTotalPriceForMovies(){
        // Skapa en instans av servicen manuellt för ett enhetstest
        RentalService service = new RentalService();

        List<Movie> movies = List.of(
            new Movie("Film 1", "Action", new BigDecimal("50.00")),
            new Movie("Film 2", "Drama", new BigDecimal("30.00"))
        );

        BigDecimal result = service.calculateTotalPriceFromMovies(movies);

        assertEquals(0, new BigDecimal("80.00").compareTo(result));
    }

    @Test
    void testSearchMoviesViaMovieService() {
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            // Förbereder data
            Transaction tx = ss.beginTransaction();
            ss.insert(new Movie("Interstellar", "Sci-Fi", new BigDecimal("99")));
            ss.insert(new Movie("The Matrix", "Action", new BigDecimal("49")));
            tx.commit();

            // Skapar Service
            MovieRepository movieRepository = new MovieRepository_(ss);
            MovieService movieService = new MovieService();

            // Anropar metod från MovieService
            List<Movie> results = movieService.searchMovies("Stellar");

            // Verifierar
            assertThat(results)
                .as("Ska hitta Interstellar via sökning på 'Stellar' tack vare %-logiken i servicen")
                .extracting(Movie::getTitle)
                .containsExactly("Interstellar");

        }
    }

    // Uppdatera filmuthyring - U

    // Ta bort filmuthyrning - D

}
