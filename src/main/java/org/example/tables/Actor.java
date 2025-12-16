package org.example.tables;

import jakarta.persistence.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    public Actor(){}

    public Actor(String firstName, String lastName, Date dateOfBirth){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    @ManyToMany(mappedBy = "actor")
    private Set<Movie> movie = new HashSet<Movie>();

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Movie> getMovie() {
        return movie;
    }

    public void setMovie(Set<Movie> movie) {
        this.movie = movie;
    }
}
