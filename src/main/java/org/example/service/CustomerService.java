package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.example.repository.CustomerRepository;
import org.example.repository.CustomerRepositoryC;
import org.example.tables.Customer;

import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    //Creates an object of CustomerRepository
    @Inject
    private CustomerRepositoryC customerRepository;


    public CustomerService() {
    }

    //Constructor for the field
    //public CustomerService(CustomerRepository customerRepository){
//        this.customerRepository = customerRepository;
//    }

    //Reads Customer from the table
    public Optional<Customer> findByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    //Saves a new Customer for the Customer table
    public void createCustomer(String firstName, String lastName, String email) {
        //input validation and stuffs then;
        customerRepository.save(new Customer(firstName, lastName, email));
        }

    }

    //Needs more operations?

