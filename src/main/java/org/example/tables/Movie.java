package org.example.tables;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String genre;

    private int duration;

    public Movie(){}

    public Movie(String title, String genre, int duration){
        this.title = title;
        this.genre = genre;
        this.duration = duration;
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
    private List<Inventory> inventoryCopies = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<Inventory> getInventoryList() {
        return inventoryCopies;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryCopies = inventoryList;
    }
}
