package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.tables.Customer;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@ApplicationScoped
public class CustomerRepositoryC {

    @Inject
    private EntityManager em;

    private void inTransactionVoid(Consumer<EntityManager> action){
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        }catch (Exception ex){
            if(tx.isActive()) tx.rollback();
            throw ex;
        }
    }

    private <T> T inTransaction(Function<EntityManager, T> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            T result = action.apply(em);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    //save and update does the same thing.
    public void save(Customer customer) {
        inTransactionVoid(manager -> manager.persist(customer));
    }
    public void update(Customer customer){
        inTransactionVoid(manager -> manager.merge(customer));
    }

    //JPQL Query, revert to .createNativeQuery if SQL is preferred
    public Optional<Customer> findByEmail(String email) {
        return inTransaction(manager -> {
            return manager.createQuery(
                    "select c From Customer c where c.email = :email",
                    Customer.class
                )
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
        });
    }

    public void delete(Customer customer) {
        inTransactionVoid(manager -> manager.remove(customer));
    }
}
