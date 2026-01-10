package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityTransaction;
import org.example.javafx.HibernateUtil;
import org.example.repository.CustomerRepository;
import org.example.repository.CustomerRepository_;
import org.example.tables.Customer;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import java.util.Optional;

@ApplicationScoped
public class CustomerService {

    //Creates an object of CustomerRepository
    //@Inject
    StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession();
    private final CustomerRepository customerRepository = new CustomerRepository_(ss);


    //Metod för att hantera inloggning av användare returnerar en användare och loggar in denna om den finns i databasen
    //Annars skapas en ny
    public Customer logInOrRegister(String firstName, String lastName, String eMail){
           if (firstName == null || lastName == null || eMail == null) {
                 throw new IllegalArgumentException("Name and email cannot be null");
                }
            if (!eMail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    throw new IllegalArgumentException("Invalid email format");
                }
        return findByEmail(eMail)
            .orElseGet(()-> {
                createCustomer(firstName, lastName, eMail);
                return findByEmail(eMail)
                    .orElseThrow(()-> new IllegalStateException(
                        "Failed to retrieve customer after creation"));
            });

    }
    //Reads Customer from the table
    public Optional<Customer> findByEmail(String email) {
        Transaction tx = ss.beginTransaction();
        try {
            var customer = customerRepository.findByEmail(email);
            tx.commit();
            return customer;
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
    }

    public Optional<Customer> findById(Long id){
        Transaction tx = ss.beginTransaction();
        try{
                var customer = customerRepository.findById(id);
                tx.commit();
                return customer;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    //Saves a new Customer for the Customer table
    public void createCustomer(String firstName, String lastName, String email) {
        //input validation and stuffs then;

        Transaction tx = ss.beginTransaction();
        try{
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
        Transaction tx = ss.beginTransaction();
        try {
            customer.ifPresent(c -> customerRepository.delete(c));
            tx.commit();
            System.out.println("deleted");
        }catch(Exception e){
            tx.rollback();
            throw e;
        }

    }
    public void updateCustomer(String firstName, String lastName, String email) {
        var customer = findByEmail(email);
        Transaction tx = ss.beginTransaction();
        try {
            if (customer.isPresent()) {
                customer.get().setFirstName(firstName);
                customer.get().setLastName(lastName);
                customerRepository.insert(customer.get());
            }
            tx.commit();
        }catch(Exception e){
            tx.rollback();
            throw e;
        }
    }
}

    //Needs more operations?

