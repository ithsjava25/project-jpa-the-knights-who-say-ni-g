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

        if (findByEmail(email) != null) {
            throw new IllegalArgumentException("Email-adress is already in use!");
        }

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);

        return customerRepository.save(customer);
    }

    //Needs more operations?

}
