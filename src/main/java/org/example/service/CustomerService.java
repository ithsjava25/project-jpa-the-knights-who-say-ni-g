package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityTransaction;
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
        EntityTransaction tx = HibernateUtil.getSessionFactory().getCurrentSession().getTransaction();
        System.out.println("tx: " + tx.toString());
        try{
            tx.begin();
            Customer newCustomer = new Customer(firstName, lastName, email);
            System.out.println(newCustomer.getFirstName());
            customerRepository.insert(newCustomer);
            tx.commit();
            System.out.println("commited");
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
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

