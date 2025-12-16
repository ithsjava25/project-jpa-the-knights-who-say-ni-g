package org.example.tables;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String firstName;

    private String lastName;

    private String email;

    // private LocalDate signUpDate?

    public Customer() {}

    public Customer(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //Koppling till Rental
    @OneToMany(mappedBy = "customer", cascade =CascadeType.PERSIST, orphanRemoval = true)
    private List<Rental> rentalList = new ArrayList<>();


}

