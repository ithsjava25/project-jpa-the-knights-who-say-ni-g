package org.example;

import org.example.javafx.HibernateUtil;
import org.example.repository.CustomerRepository;
import org.example.repository.CustomerRepository_;
import org.example.repository.MovieRepository;
import org.example.repository.MovieRepository_;
import org.example.service.CustomerService;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @Test
    void verifyHibernateConnection() {
        System.out.println("Försöker hämta SessionFactory");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("SessionFactory hämtad!");

        try (Session session = sessionFactory.openSession()) {
            assertThat(session.isConnected()).isTrue();

            Object result = session.createNativeQuery("SELECT 1").getSingleResult();
            assertThat(result.toString()).isEqualTo("1");
        }
    }


    @Test
    void loginUser_shouldCreateNewUser_WhenEmailDoesNotExist() {

        CustomerService service = new CustomerService();

        String testEmail = "ny_anvandare@gmail.com";
        String fName = "Anna";
        String lName = "Andersson";

        Customer result = service.logInOrRegister(fName, lName, testEmail);


        assertThat(result).isNotNull();
        assertThat(result.getCustomerId()).as("Kunden borde ha fått ett genererat ID").isNotNull();
        assertThat(result.getEmail()).isEqualTo(testEmail);
        assertThat(result.getFirstName()).isEqualTo(fName);

        try (StatelessSession verifySession = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Customer dbCustomer = (Customer) verifySession.createNativeQuery(
                    "SELECT * FROM customer WHERE EMAIL = :email", Customer.class)
                .setParameter("email", testEmail)
                .getSingleResult();
            assertThat(dbCustomer).isNotNull();

        }
    }

   @Test
    void loginUser_shouldReturnExistingCustomer_whenEmailExists(){
        String existingEmail = "befintligUser@gmail.com";
        Long existingId;

        try (StatelessSession setupSession = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Transaction tx = setupSession.beginTransaction();
            Customer existingC = new Customer("Bob", "Bobsson", existingEmail);
            setupSession.insert(existingC);
            tx.commit();
            existingId = existingC.getCustomerId();

            CustomerService service = new CustomerService();
            Customer result = service.logInOrRegister("Bob", "Bobsson", existingEmail);

            assertThat(result).isNotNull();
            assertThat(result.getCustomerId())
                .as("Borde returnera samma ID som redan finns i databasen")
                .isEqualTo(existingId);

            assertThat(result.getEmail()).isEqualTo(existingEmail);
        }
    }

    @Test
    void loginOrRegister_ShouldThrowException_WhenEmailIsInvalid() {

        CustomerService service = new CustomerService();
        assertThatThrownBy(() -> service.logInOrRegister("Anna", "A", "ogiltig-email"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid email format");

    }

    // Todo: Se över efter ändrad projekt struktur
//    @Test
//    void createNewRental() {
//        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
//            Transaction tx = ss.beginTransaction();
//            try {
//                // Skapar test-kund och test-film och sparar
//            Customer customer = new Customer("Förnamn", "Efternamn", "test@mail.com");
//            ss.insert(customer);
//
//            Movie movie = new Movie("Interstellar", "Sci-Fi", new BigDecimal("99"));
//            ss.insert(movie);
//
//                // Create rental-objekt
//            LocalDateTime now = LocalDateTime.now();
//            Rental rental = new Rental();
//            rental.setCustomer(customer);
//            rental.setRentalDate(now);
//            rental.setReturnDate(now.plusHours(24));
//            rental.setTotalRentalPrice(new BigDecimal("50.0"));
//            // Sparar rental-object
//            ss.insert(rental);
//
//            // Skapar kopplingstabellen manuellt
//                // --> För att få in data i kopplingstabellen när vi använder Stateless Session
//            ss.createNativeQuery(
//                    "INSERT INTO movie_rental (rental_id, movie_id) VALUES (:rId, :mId)")
//                .setParameter("rId", rental.getRentalId())
//                .setParameter("mId", movie.getId())
//                .executeUpdate();
//
//            tx.commit();
//
//            // Verifiering
//            assertThat(rental.getRentalId()).isNotNull();
//            // Kontrollerar kopplingen
//            Object count = ss.createNativeQuery("SELECT COUNT(*) FROM movie_rental WHERE rental_id = :rId")
//                .setParameter("rId", rental.getRentalId())
//                .getSingleResult();
//
//            assertThat(((Number) count).intValue()).isEqualTo(1);
//
//        } catch (Exception e) {
//                tx.rollback();
//                throw e;
//            }
//        }
//    }

    // räkna total price
//    @Test
//    void calculateTotalPriceForMovies(){
//        // Skapa en instans av servicen manuellt för ett enhetstest
//        RentalService service = new RentalService();
//
//        List<Movie> movies = List.of(
//            new Movie("Film 1", "Action", new BigDecimal("50.00")),
//            new Movie("Film 2", "Drama", new BigDecimal("30.00"))
//        );
//
//        BigDecimal result = service.calculateTotalPriceFromMovies(movies);
//
//        assertEquals(0, new BigDecimal("80.00").compareTo(result));
//    }

    @Test
    void searchMoviesViaMovieService_shouldFindBothTitleAndGenre() {
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            // Förbereder data
            Transaction tx = ss.beginTransaction();
            ss.insert(new Movie("Interstellar", "Sci-Fi", new BigDecimal("99")));
            ss.insert(new Movie("The Matrix", "Action", new BigDecimal("49")));
            tx.commit();

            // Skapar Service
            MovieService movieService = new MovieService();

            // Test 1: Sökning via Title
            List<Movie> titleResult = movieService.searchMovies("Stellar");
            assertThat(titleResult)
                .extracting(Movie::getTitle)
                .contains("Interstellar");

            // Test 2: Sökning via Genre
            List<Movie> genreResults = movieService.searchMovies("Sci-Fi");
            assertThat(genreResults)
                .extracting(Movie::getTitle)
                    .contains("Interstellar");

        }
    }

    // Uppdatera filmuthyring med nytt pris och förlängd tid - U


    // Ta bort filmuthyrning, när 24h alt 48h passerat? - D

}
