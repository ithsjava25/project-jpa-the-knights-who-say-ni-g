package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.javafx.HibernateUtil;
import org.example.repository.CustomerRepository;
import org.example.repository.CustomerRepository_;
import org.example.tables.Customer;

import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    //Creates an object of CustomerRepository
    //@Inject
    private CustomerRepository customerRepository = new CustomerRepository_(HibernateUtil.getSessionFactory().openStatelessSession());


    //Reads Customer from the table
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    //Saves a new Customer for the Customer table
    public void createCustomer(String firstName, String lastName, String email) {
        //input validation and stuffs then;
        //customerRepository.save(new Customer(firstName, lastName, email));
        customerRepository.insert(new Customer(firstName, lastName, email));
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

