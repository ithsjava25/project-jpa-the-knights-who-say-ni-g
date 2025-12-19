package org.example.tables;

import jakarta.persistence.*;

//Entity
//@Table(name = "movie_rental")
public class Movierental {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieRentalId;

    public Movierental() {}

    /*
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;

     */

    /*
    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

     */
}
