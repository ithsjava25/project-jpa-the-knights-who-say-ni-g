package org.example.tables;

import jakarta.persistence.*;
import javafx.collections.ObservableArray;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "item_id")
    private int itemId;

    private String title;

    private String genre;

    private BigDecimal price = new BigDecimal(0);


    public Movie(){}

    public Movie(String title, String genre, BigDecimal price) {
        this.title = title;
        this.genre = genre;
        this.price = price;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_actor",
        joinColumns = @JoinColumn(name="movie_id"),
        inverseJoinColumns = @JoinColumn(name="actor_id"))
    private Set<Actor> actor = new HashSet<>();


    @OneToMany(mappedBy ="movie")
    private Set<RentalMovie> rentals = new HashSet<>();




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

    public Set<RentalMovie> getRentals() {
        return rentals;
    }

    public void setRentals(Set<RentalMovie> inventoryList) {
        this.rentals = inventoryList;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie movie)) return false;
        return itemId == movie.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemId);
    }
}
