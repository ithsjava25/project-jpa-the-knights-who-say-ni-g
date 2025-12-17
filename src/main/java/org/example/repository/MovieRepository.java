package org.example.repository;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import org.example.tables.Movie;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    boolean existsById(Integer id);

    // --FIND--
    Optional<Movie> findByTitle(String title);
    List<Movie> findByGenre(String genre);
    List<Movie> findByDuration(int duration);
    List<Movie> findByPrice(BigDecimal price);

    List<Movie> findByDirector_FirstNameAndDirector_LastName(String firstName, String lastName);
    List<Movie> findByActor_FirstNameAndActor_LastName(String firstName, String lastName);


    // Addera dessa i MovieService --> findByTitle
    List<Movie> findTotal_stock();
    List<Movie> findAvailable_stock();

    // --COUNT--
    long countByGenre(String genre);
    long countByDuration(int duration);

    long countByDirector_FirstNameAndDirector_LastName(String firstName,  String lastName);
    long countByActor_FirstNameAndActor_LastName(String firstName, String lastName);


}
