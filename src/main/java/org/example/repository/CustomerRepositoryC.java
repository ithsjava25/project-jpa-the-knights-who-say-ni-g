package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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

    public void save(Customer customer){
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(customer);
            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }
    }
}
