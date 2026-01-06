package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.repository.CustomerRepositoryC;
import org.example.tables.Customer;

import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    //Creates an object of CustomerRepository
    @Inject
    private CustomerRepositoryC customerRepository;


    //Reads Customer from the table
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    //Saves a new Customer for the Customer table
    public void createCustomer(String firstName, String lastName, String email) {
        //input validation and stuffs then;
        customerRepository.save(new Customer(firstName, lastName, email));

    }

    public void deleteCustomer(String email) {
        var customer = findByEmail(email);
        customer.ifPresent(c -> customerRepository.delete(c));
    }
    public void updateCustomer(String firstName, String lastName, String email) {
        var customer = findByEmail(email);
        if(customer.isPresent()) {
            customer.get().setFirstName(firstName);
            customer.get().setLastName(lastName);
            customerRepository.save(customer.get());
        }
    }
}

    //Needs more operations?

