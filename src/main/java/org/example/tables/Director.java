package org.example.tables;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//@Entity
//@Table(name = "director")
public class Director {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String firstName;

    private String lastName;

    public Director(){}
    public Director(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //@ManyToMany(mappedBy = "director")
    private Set<Movie> movie = new HashSet<Movie>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String setLastName(String lastName) {
        return this.lastName = lastName;
    }

    public Set<Movie> getMovie() {
        return movie;
    }

    public void setMovie(Set<Movie> movie) {
        this.movie = movie;
    }
}
