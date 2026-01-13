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
    @Column(name= "rental_id")
    private Long rentalId;

    @Column(name= "rental_date")
    private LocalDateTime rentalDate;

    @Column(name= "return_date")
    private LocalDateTime returnDate;

    @Column(name= "total_rental_price")
    private BigDecimal totalRentalPrice;

    @Column(name = "additional_cost")
    private BigDecimal additionalCost = BigDecimal.valueOf(0);


    public Rental() {
        this.rentalDate = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    //ersätta many to many - egen entitet med id sammansatt nyckel film id och rental id
    @ManyToMany
    @JoinTable(name = "movie_rental",
        joinColumns = @JoinColumn(name = "rental_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id"))
    //Två kolumner för pris och återlämning
    private Set<Movie> movierental = new HashSet<>();
    //Blir det många listor om man har List<Movierental> här?
    //Eller kan man slå ihop dem med att ha List<Movie> istället?


    public Customer getCustomer() {
        return customer;
    }

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

    public BigDecimal getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(BigDecimal additionalCost) {
        this.additionalCost = additionalCost;
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




