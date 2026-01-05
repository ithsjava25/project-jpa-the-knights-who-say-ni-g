package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.tables.Movie;
import org.example.tables.Rental;

@ApplicationScoped
public class RentalRepositoryC {

    @Inject
    private EntityManager em;

    public void createRental(Rental rental){
        em.persist(rental);
    }

}
