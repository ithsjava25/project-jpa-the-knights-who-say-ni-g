package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.tables.Customer;

import java.util.Optional;

@ApplicationScoped
public class CustomerRepositoryC {

    @Inject
    private EntityManager em;

    public Optional<Customer> findByEmail(String email){
        Customer customer = em.find(Customer.class, email);
        return Optional.ofNullable(customer);
    }

    public void save(Customer customer){}
}
