package org.example.tables;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "rental_movie")
public class RentalMovie {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name= "movie_rental_id")
        private Long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "rental_id")
        private Rental rental;

        @ManyToOne(optional = false)
        @JoinColumn(name = "movie_id")
        private Movie movie;

        @Column(nullable = false, name = "base_price")
        private BigDecimal basePrice; //Grundpris för filmen

        @Column(nullable = false, name = "return_date")
        private LocalDateTime returnDate; //returdatum per film

        @Column(nullable = false,name = "additional_cost")
        private BigDecimal additionalCost = BigDecimal.ZERO; // t.ex. 29 kr per förlängning - default 0

    public BigDecimal getTotalPrice() {
        return basePrice.add(additionalCost);
    }

    public BigDecimal getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(BigDecimal additionalCost) {
        this.additionalCost = additionalCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public BigDecimal getPrice() {
        return basePrice;
    }

    public void setPrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public LocalDateTime getRentedAt() {
        return returnDate;
    }

    public void setRentedAt(LocalDateTime rentedAt) {
        this.returnDate = rentedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RentalMovie that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(rental, that.rental) && Objects.equals(movie, that.movie) && Objects.equals(basePrice, that.basePrice) && Objects.equals(returnDate, that.returnDate) && Objects.equals(additionalCost, that.additionalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rental, movie, basePrice, returnDate, additionalCost);
    }
}





