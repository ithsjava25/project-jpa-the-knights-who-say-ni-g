package org.example.service;

import org.example.repository.CustomerRepository;
import org.example.tables.Customer;

//@ApplicationScooped
public class CustomerService {


    //Creates an object of CustomerRepository
    private CustomerRepository customerRepository;

    //Constructor for the field
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    //Reads Customer from the table
    public Customer findByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    //Saves a new Customer for the Customer table
    public Customer createCustomer(String firstName, String lastName, String email) {


        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);

        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Email address is already in use!");
        }
    }

    //Needs more operations?

}
