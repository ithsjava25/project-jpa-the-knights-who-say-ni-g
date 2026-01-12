package org.example.tables;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "rental")
public class Rental {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private BigDecimal totalRentalPrice;

    // private Long customerId;

    public Rental() {
        this.rentalDate = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "movie_rental",
        joinColumns = @JoinColumn(name = "rental_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movierental = new HashSet<>();
    //Blir det många listor om man har List<Movierental> här?
    //Eller kan man slå ihop dem med att ha List<Movie> istället?


    public Customer getCustomer() {
        return customer;
    }

    // public Long getId() {return customerId;}

    //  public void setId(Long customerId) { this.customerId = customerId;}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

        public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getTotalRentalPrice() {
        return totalRentalPrice;
    }

    public void setTotalRentalPrice(BigDecimal totalRentalPrice) {
        this.totalRentalPrice = totalRentalPrice;
    }

    public Set<Movie> getMovierental() {
        return movierental;
    }

    public void setMovierental(Set<Movie> movierental) {
        this.movierental = movierental;
    }


    public void addMovie(Movie movie) {
        if (movie != null) {
            this.movierental.add(movie);
            movie.getRentals().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rental rental)) return false;
        return Objects.equals(rentalId, rental.rentalId) && Objects.equals(rentalDate, rental.rentalDate) && Objects.equals(totalRentalPrice, rental.totalRentalPrice) && Objects.equals(customer, rental.customer) && Objects.equals(movierental, rental.movierental);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, rentalDate, totalRentalPrice, customer, movierental);
    }
}




