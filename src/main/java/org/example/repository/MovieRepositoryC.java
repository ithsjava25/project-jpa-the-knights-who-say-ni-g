package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.example.tables.Movie;

import java.util.Optional;

@ApplicationScoped //Tells Weld it only needs one instance
public class MovieRepositoryC {

    @Inject
    private EntityManager em;

    public Optional<Movie> findByTitle(String title){
        Movie movie = em.find(Movie.class, title);
        return Optional.ofNullable(movie);
    }
    public Optional<Movie> findById(int id){
        Movie movie = em.find(Movie.class, id);
        return Optional.ofNullable(movie);
    }
    public void save(Movie movie){
        em.persist(movie);
    }
    public void delete(Movie movie){
        em.remove(movie);
    }

    //Not quite right NamedQuery
//    public List<Movie> findAll(){
//        return em.createNamedQuery("SELECT m FROM Movie m", Movie.class).getResultList();
//    }

}
