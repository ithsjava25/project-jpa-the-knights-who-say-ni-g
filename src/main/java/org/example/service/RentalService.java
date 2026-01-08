package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityTransaction;
import org.example.javafx.HibernateUtil;
import org.example.repository.CustomerRepository;
import org.example.repository.RentalRepository;
import org.example.repository.RentalRepository_;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;
import org.hibernate.StatelessSession;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RentalService {

    StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession();
    private final RentalRepository rentalRepository = new RentalRepository_(ss);
    private final EntityTransaction tx = ss.getTransaction();

    // get all rentals for a costumer? Via Repository

    // Create a rental for a customer, information sent to Repository via save
    public void rentMovies(Customer customer, List<Movie> movies) {
        // Check the customer (get customer id)?
        try {
            tx.begin();
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
            // Connect movies to the rental
            for (Movie movie : movies) {
                rental.addMovie(movie);

                rentalRepository.createRental(rental);
                tx.commit();
            }

        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        }
    }

    //Calculate total rent price based on prices from MovieRepository
    public BigDecimal calculatePrice(List<Movie> movies) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Movie movie : movies) {
//            if(movie.getPrice() != null) {
//                sum = sum.add(movie.getPrice());
//            }
        }
        return sum;
    }
}
