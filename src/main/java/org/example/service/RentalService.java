package org.example.service;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.repository.CustomerRepository;
import org.example.repository.MovieRepository;
import org.example.repository.RentalRepository;
import org.example.tables.Customer;
import org.example.tables.Movie;
import org.example.tables.Rental;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RentalService {

    @Inject
    RentalRepository rentalRepository;
    @Inject
    EntityManager em;


    public RentalService(CustomerRepository customerRepository, MovieRepository movieRepository, RentalRepository rentalRepository) {
        //this.customerRepository = customerRepository;
        //this.movieRepository = movieRepository;
        this.rentalRepository = rentalRepository;
    }

    public RentalService() {
    }


    // get all rentals for a costumer? Via Repository

    // Create a rental for a customer, information sent to Repository via save
    @Transactional
    public Rental rentMovies(Customer customer, List<Movie> movies){
        // Check the customer (get customer id)?
        Customer verifiedCustomer = null;
        try {
            //verifiedCustomer = customerRepository.findByEmail(customer.getEmail());
        }catch(Exception e){
            System.out.println("The customer does not exist");
        }

        Rental rental = new Rental();
        rental.setCustomer(verifiedCustomer);
        rental.setRentalDate(LocalDateTime.now());

        for (Movie movie : movies){
           // rental.setMovierental(movies);
            // rental.movierental.add(movie); // add as a method in Rental
        //    public void addMovie(Movie movie) {
        //    Movierental mr = new Movierental();
        //    mr.setMovie(movie);
        //    mr.setRental(this);
        //    this.movieRentals.add(mr);
      }
        return rentalRepository.createRental(rental);
    }

    //Calculate total rent price based on prices from MovieRepository
    public BigDecimal calculatePrice(List<Movie> movies){
            BigDecimal sum = BigDecimal.ZERO;
        for(Movie movie:movies) {
            if(movie.getPrice() != null) {
                sum = sum.add(movie.getPrice());
            }
        }
        return sum;
    }
}
