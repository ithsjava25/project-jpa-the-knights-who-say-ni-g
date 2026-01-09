package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.javafx.HibernateUtil;
import org.example.repository.RentalRepository;
import org.example.repository.RentalRepository_;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RentalService {

    StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession();
    private final RentalRepository rentalRepository = new RentalRepository_(ss);


    // get all rentals for a costumer? Via Repository

    // Create a rental for a customer, information sent to Repository via save
    public void rentMovies(Customer customer, List<Movie> movies) {
        // Check the customer (get customer id)?
        Transaction tx = ss.beginTransaction();
        try {
            //         Customer verifiedCustomer = null;
//            try {
//                verifiedCustomer = CustomerRepository.findByEmail(customer.getEmail());
//            } catch (Exception e) {
//                System.out.println("The customer does not exist");
//            }
            // Create rental-objekt
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setRentalDate(LocalDateTime.now());

            // Calculates total price for movie rentals
            rental.setTotalRentalPrice(calculatePrice(movies));

            // Create a Rental to get an ID - Because of StatelessSession we need to insert every object
            ss.insert(rental);

            for (Movie movie : movies) {
                // Manuell SQL eftersom det inte finns någon entitet för kopplingen
                ss.createNativeQuery(
                    "INSERT INTO movie_rental (rental_id, movie_id) VALUES (:rId, :mId)")
                    .setParameter("rId", rental.getRentalId())
                    .setParameter("mId", movie.getId())
                    .executeUpdate();
                }

                    tx.commit();
            System.out.println("Movie rental created with total price: " + rental.getTotalRentalPrice());
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Could not create rental", e);
        }

    }
    // Return a 'list' in view for the customer to see which movies he/she rents
    // public List<>
    public List<Movie> getRentedMoviesByCustomer(Customer customer) {
        //Returnerar en tom lista om det inte fins några uthyrda filmer
        if (customer == null) return List.of();
        // hämtar alla filmer som är kopplade till kunden via movie_rental och rental-tabellerna för att visa i vyn
        return ss.createNativeQuery(
                "SELECT * FROM movie m " +
                    "JOIN movie_rental mr ON m.itemId = mr.movie_id " +
                    "JOIN rental r ON mr.rental_id = r.rentalId " +
                    "WHERE r.customer_id = :cId", Movie.class)
            .setParameter("cId", customer.getCustomerId())
            .getResultList();
    }

    //Calculate total rent price based on prices from MovieRepository
    public BigDecimal calculatePrice(List<Movie> movies) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Movie movie : movies) {
            if(movie.getPrice() != null) {
                sum = sum.add(movie.getPrice());
           }
        }
        return sum;
    }

    public void deleteOldRentals() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        Transaction tx = ss.beginTransaction();

        try {
            // Finds all rentals older than 24h
            List<Rental> oldRentals = rentalRepository.findByRentalDateLessThan(cutoff);

            // Loops through and deletes
            // Måste rensa kopplingstabellen manuellt först via NativeQuery pga StatelessSession
            for (Rental rental : oldRentals) {
                ss.createNativeQuery("DELETE from movie_rental WHERE rental_id = :rId")
                    .setParameter("rId", rental.getRentalId())
                    .executeUpdate();

                rentalRepository.delete(rental);
            }
            tx.commit();
            System.out.println("Cleared " +  oldRentals.size() + " old rentals.");
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Could not clear old rentals", e);
        }

    }
}
