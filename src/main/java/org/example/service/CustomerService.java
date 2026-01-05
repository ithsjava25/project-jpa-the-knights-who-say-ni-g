package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.example.repository.CustomerRepository;
import org.example.tables.Customer;

@ApplicationScoped
public class CustomerService {

    //Creates an object of CustomerRepository
    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private EntityManager em;

    public CustomerService() {
    }

    //Constructor for the field
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    //Reads Customer from the table
    public Customer findByEmail(String email){
        return customerRepository.findByEmail(email);
    }

    //Saves a new Customer for the Customer table
    public void createCustomer(String firstName, String lastName, String email) {

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Customer customer = new Customer(firstName, lastName, email);
            customerRepository.save(customer);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }

//        try {
//            return customerRepository.save(customer);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Email address is already in use!");
//        }
    }

    //Needs more operations?

}
