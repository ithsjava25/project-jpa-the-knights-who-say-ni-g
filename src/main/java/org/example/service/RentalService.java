package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.javafx.HibernateUtil;
import org.example.javafx.controller.RentedMovieView;
import org.example.repository.RentalRepository;
import org.example.repository.RentalRepository_;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.example.tables.RentalMovie;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RentalService {

    StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession();
    private final RentalRepository rentalRepository = new RentalRepository_(ss);


    // Create a rental for a customer, information sent to Repository via save
    public void rentMovies(Customer customer, List<Movie> movies) {
        // Check the customer (get customer id)?
        Transaction tx = ss.beginTransaction();
        try {
            LocalDateTime now = LocalDateTime.now();
            // Create rental-objekt
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(now);

            // Saves a Rental to get an ID - Because of StatelessSession we need to insert every object
            ss.insert(rental);

            //Skapar 1 RentalMovie per film
            for (Movie movie : movies) {
                RentalMovie rm = new RentalMovie();
                rm.setRental(rental);
                rm.setMovie(movie);
                rm.setPrice(movie.getPrice());
                rm.setRentedAt(now.plusHours(24));
                rm.setAdditionalCost(BigDecimal.ZERO);

                rental.getMovierental().add(rm);
                //sparar ett rental_movie objekt
                ss.insert(rm);
            }


            tx.commit();
            System.out.println("Movie rental created");
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Could not create rental", e);
        }

    }

    //Projection, istället för att hämta en entitet - hämta ett dto-objekt
    // Return a 'list' of DTO-objects regarding the customer to see which movies he/she rents
    public List<RentedMovieView> getRentedMoviesByCustomer(Customer customer) {
        //Returnerar en tom lista om det inte fins några uthyrda filmer
        if (customer == null) return List.of();
        // hämtar alla filmer som är kopplade till kunden via movie_rental och rental-tabellerna för att visa i vyn

        return ss.createQuery(
                "SELECT new org.example.javafx.controller.RentedMovieView(" +
                    "rm.id, m.title, rm.basePrice, rm.returnDate, rm.additionalCost) " +
                    "FROM RentalMovie rm " +
                    "JOIN rm.movie m " +
                    "JOIN rm.rental r " +
                    "WHERE r.customer.customerId = :cId", RentedMovieView.class)
            .setParameter("cId", customer.getCustomerId())
            .getResultList();

    }

    //For MySQL
    //Förnyar en uthyrning med 24h, lägger då på 29kr, queryn uppdaterar raden för valt id
    //Gör till en fråga i RentalMovieRepo? Fungerar ej, för kompliserad fråga för Jakarta Data
    //coalesce en variant av switch med cases
    public void renewRentalMovie(Long rentalMovieId) {
        Transaction tx = ss.beginTransaction();
        try {
            int updated = ss.createNativeQuery("""
                    update rental_movie
                    set return_date = DATE_ADD(return_date, INTERVAL 24 HOUR),
                        additional_cost = coalesce(additional_cost, 0) + 29
                    where movie_rental_id = :rentalId
                    """)
                .setParameter("rentalId", rentalMovieId)
                .executeUpdate();
            if(updated == 0){
                throw new IllegalArgumentException("RentalMovie not found: " + rentalMovieId);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Could not renew rental. ", e);
        }
    }

    public void deleteExpiredRentalMovies() {
        //SQL now current timestamp
        //Flyttas ej in till RentalMovieRepo då det är en för komplicerad fråga för Jakarta Data
        LocalDateTime now = LocalDateTime.now();
        Transaction tx = ss.beginTransaction();

        try {
            int deleted = ss.createNativeQuery("""
                    delete from rental_movie
                    where return_date < :now""")
                .setParameter("now", now)
                .executeUpdate();
            tx.commit();
            System.out.println("Deleted " + deleted + " old expired rental movies.");
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Could not delete expired rental movies", e);
        }

    }
    public void deleteEmptyRentals() {
        Transaction tx = ss.beginTransaction();

        try {
            int deleted = ss.createNativeQuery("""
            delete from rental
            where rental_id not in(
                select distinct rental_id
                from rental_movie
            )
""")
                .executeUpdate();
            tx.commit();
            System.out.println("Deleted " + deleted + " empty rentals.");
        }catch(Exception e) {
            tx.rollback();
            throw new RuntimeException("Could not delete empty rentals", e);
        }
    }
    // RentalService
    // 1) delete expired rental_movies
    // 2) delete rentals that became empty
    public void cleanupRentals() {
        deleteExpiredRentalMovies();
        deleteEmptyRentals();
    }

}
