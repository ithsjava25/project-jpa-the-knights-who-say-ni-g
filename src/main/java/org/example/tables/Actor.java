package org.example.tables;

import jakarta.persistence.*;

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

    @ManyToMany(mappedBy = "actor")
    private Set<Movie> movie = new HashSet<Movie>();
}
