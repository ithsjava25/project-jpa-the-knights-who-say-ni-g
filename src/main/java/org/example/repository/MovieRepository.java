package org.example.repository;

import jakarta.data.repository.*;
import org.example.tables.Movie;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    //boolean existsById(Integer id);

    // To support smart-search in search field
    // Query needs to be written manually here to function
   @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(:q) OR LOWER(m.genre) LIKE LOWER(:q)")
   List<Movie> smartSearch(@Param("q") String q);

    @Find
    List<Movie> findByTitleContainingIgnoringCase(String title);

    @Find
    List<Movie> findByGenreIgnoringCase(String genre);

}
