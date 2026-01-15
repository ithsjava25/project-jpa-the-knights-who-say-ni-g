package org.example;

import org.example.javafx.HibernateUtil;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.example.tables.RentalMovie;
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
import static org.junit.jupiter.api.Assertions.assertNull;

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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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
            existingId = (Long) setupSession.insert(existingC);
            tx.commit();

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


    @Test
    void rentMovies_ShouldCreateRentalAndRentalMoviesInDatabase() {
        // Förbereder data
        Customer customer = new Customer("Förnamn", "Efternamn", "rent-test@mail.com");
        Movie movie = new Movie("Interstellar", "Sci-Fi", new BigDecimal("99"));

        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Transaction tx = ss.beginTransaction();
            ss.insert(customer);
            ss.insert(movie);
            tx.commit();
        }

        // Anropa RentalService
        RentalService service = new RentalService();
        service.rentMovies(customer, List.of(movie));

        // Kontrollerar att datan hamnar i databasen
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            // Hämtar rental kopplat till kunden
            String sql = "SELECT * FROM rental WHERE customer_id = :cId";
            Rental savedRental = (Rental) ss.createNativeQuery(sql, Rental.class)
                .setParameter("cId", customer.getCustomerId())
                .getSingleResult();

            assertThat(savedRental).isNotNull();

            // Kontrollerar att RentalMovie skapades korrekt
            String rmSql = "SELECT COUNT(*) FROM rental_movie WHERE rental_id = :rId";
            Object count = ss.createNativeQuery(rmSql)
                .setParameter("rId", savedRental.getRentalId())
                .getSingleResult();

            assertThat(((Number) count).intValue()).isEqualTo(1);

        }
    }


    @Test
    void renewRentalMovies_shouldIncreaseDateAndCost() {
        Long rentalMovieID;
        LocalDateTime currentDate = LocalDateTime.now();
        BigDecimal initialAdditionalCost = new BigDecimal("0");

        // Skapar data som ska uppdateras
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Transaction tx = ss.beginTransaction();

            // Skapar kund
            Customer customer = new Customer("Förnamn", "Efternamn", "renew-test@mail.com");
            ss.insert(customer);

            // Skapar film
            Movie movie = new Movie("Interstellar", "Sci-Fi", new BigDecimal("99"));
            ss.insert(movie);

            // Kopplar kund till rental
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(currentDate);
            ss.insert(rental);

            // Skapar RentalMovie
            RentalMovie rm = new RentalMovie();
            rm.setRental(rental);
            rm.setMovie(movie);
            rm.setPrice(new BigDecimal("99"));

            rm.setRentedAt(currentDate);
            rm.setAdditionalCost(initialAdditionalCost);

            // Sparar och hämtar genererat ID
            rentalMovieID = (Long) ss.insert(rm);
            tx.commit();
        }

        // Anropar metoden via RentalService
        RentalService service = new RentalService();
        service.renewRentalMovie(rentalMovieID);

        // Verifierar att uppdateringen skett korrekt
        try (StatelessSession statelessSession =  HibernateUtil.getSessionFactory().openStatelessSession()) {
            RentalMovie updatedRm = (RentalMovie) statelessSession.createNativeQuery(
                "SELECT * FROM rental_movie WHERE movie_rental_id = :id",RentalMovie.class)
                .setParameter("id", rentalMovieID)
                .getSingleResult();

            // Kontrollerar att kostnaden ökat med 29 kr från baseprice
            assertThat(updatedRm.getAdditionalCost()).isEqualByComparingTo(new BigDecimal("29"));

            //Kontrollera att datumet flyttats fram 24 timmar
            assertThat(updatedRm.getRentedAt()).isEqualTo(currentDate.plusHours(24));

        }
    }


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


    // Ta bort filmuthyrning, när 24h alt 48h passerat? - D
    @Test
    void deleteMovieRental_ShouldRemoveRentalFromDatabase() {
        Long rentalMovieID;

        // Skapa en fixerad tidpunkt
        LocalDateTime rentedAt = LocalDateTime.now();
        LocalDateTime rentalDate = LocalDateTime.now().minusHours(24);

        // Skapar data som ska uppdateras
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Transaction tx = ss.beginTransaction();

            // Skapar kund
            Customer customer = new Customer("Förnamn", "Efternamn", "renew-test@mail.com");
            ss.insert(customer);

            // Skapar film
            Movie movie = new Movie("Interstellar", "Sci-Fi", new BigDecimal("99"));
            ss.insert(movie);

            // Kopplar kund till rental
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(rentalDate);//15/1 kl 10
            ss.insert(rental);

            // Skapar RentalMovie
            RentalMovie rm = new RentalMovie();
            rm.setRental(rental);
            rm.setMovie(movie);
            rm.setPrice(new BigDecimal("99"));
            rm.setRentedAt(rentedAt); //16/1 kl

            // Sparar och hämtar genererat ID
            rentalMovieID = (Long) ss.insert(rm);
            tx.commit();
        }

        RentalService service = new RentalService();
        service.deleteExpiredRentalMovies();

            try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
                RentalMovie deleted = ss.get(RentalMovie.class, rentalMovieID);

            //Kolla att filmen/Rental har försvunnit
            assertNull(deleted, "Expired RentalMovie should be deleted");
        }
    }

    @Test
    void deleteEmptyRentals() {
        Long rentalID;

        // Skapa en fixerad tidpunkt
        LocalDateTime rentedAt = LocalDateTime.now();
        LocalDateTime rentalDate = LocalDateTime.now().minusHours(23);


        // Skapar data som ska uppdateras
        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Transaction tx = ss.beginTransaction();

            // Skapar kund
            Customer customer = new Customer("Förnamn", "Efternamn", "renew-test@mail.com");
            ss.insert(customer);

            // Skapar film
            Movie movie = new Movie("Interstellar", "Sci-Fi", new BigDecimal("99"));
            ss.insert(movie);

            // Kopplar kund till rental
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(rentalDate);
            rentalID = (Long) ss.insert(rental);

            tx.commit();
        }

        RentalService service = new RentalService();
        service.deleteEmptyRentals();


        try (StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession()) {
            Rental deleted = ss.get(Rental.class, rentalID);

            //Kolla att Rental har försvunnit
            assertNull(deleted, "Expired Rental should be deleted");

        }
    }
}
