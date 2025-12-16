package org.example.tables;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_id;

    private String title;

    private String genre;

    private int duration;

    private BigDecimal price;

    private int total_stock;

    private int available_stock;

    public Movie(){}

    public Movie(String title, String genre, int duration, BigDecimal price,  int total_stock, int available_stock) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.price = price;
        this.total_stock = total_stock;
        this.available_stock = available_stock;
    }

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_director",
                joinColumns = @JoinColumn(name="movie_id"),
                inverseJoinColumns = @JoinColumn(name="director_id"))
    private Set<Director> director = new HashSet<Director>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_actor",
        joinColumns = @JoinColumn(name="movie_id"),
        inverseJoinColumns = @JoinColumn(name="actor_id"))
    private Set<Actor> actor = new HashSet<Actor>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST)
    private List<Movierental> inventoryCopies = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return item_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getTotal_stock() {
        return total_stock;
    }

    public void setTotal_stock(int total_stock) {
        this.total_stock = total_stock;
    }

    public int getAvailable_stock() {
        return available_stock;
    }

    public void setAvailable_stock(int available_stock) {
        this.available_stock = available_stock;
    }

    public List<Movierental> getInventoryCopies() {
        return inventoryCopies;
    }

    public void setInventoryCopies(List<Movierental> inventoryCopies) {
        this.inventoryCopies = inventoryCopies;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<Director> getDirector() {
        return director;
    }

    public void setDirector(Set<Director> director) {
        this.director = director;
    }

    public Set<Actor> getActor() {
        return actor;
    }

    public void setActor(Set<Actor> actor) {
        this.actor = actor;
    }

    public List<Movierental> getInventoryList() {
        return inventoryCopies;
    }

    public void setInventoryList(List<Movierental> inventoryList) {
        this.inventoryCopies = inventoryList;
    }
}
