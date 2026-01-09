package org.example.tables;

import jakarta.persistence.*;
import javafx.collections.ObservableArray;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    private String title;

    private String genre;

    private BigDecimal price = new BigDecimal(0);


    public Movie(){}

    public Movie(String title, String genre, int duration, BigDecimal price,  int total_stock, int available_stock) {
        this.title = title;
        this.genre = genre;
        this.price = price;
    }

    @ManyToMany
    @JoinTable(name = "movie_actor",
        joinColumns = @JoinColumn(name="movie_id"),
        inverseJoinColumns = @JoinColumn(name="actor_id"))
    private Set<Actor> actor = new HashSet<>();


    @ManyToMany(mappedBy ="movierental")
    private Set<Rental> rentals = new HashSet<>();




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return itemId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getGenre() {
        return genre;
    }

   public void setGenre(String genre) {
       this.genre = genre;
   }


    public Set<Actor> getActor() {
        return actor;
    }

    public void setActor(Set<Actor> actor) {
        this.actor = actor;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> inventoryList) {
        this.rentals = inventoryList;
    }

}
