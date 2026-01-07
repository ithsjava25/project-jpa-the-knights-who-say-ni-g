package org.example.repository;

import jakarta.data.repository.*;
import org.example.tables.Movie;

import java.util.Optional;

@Repository

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    //boolean existsById(Integer id);

    // --FIND--
    @Find
    Optional<Movie> findByTitle(String title);
//    List<Movie> findByGenre(String genre);
//    List<Movie> findByDuration(int duration);
//    List<Movie> findByPrice(BigDecimal price);
//
//    List<Movie> findByDirector_FirstNameAndDirector_LastName(String firstName, String lastName);
//    List<Movie> findByActor_FirstNameAndActor_LastName(String firstName, String lastName);
//
//
//    // --COUNT--
//    long countByGenre(String genre);
//    long countByDuration(int duration);
//
//    @Query("""
//    select count(distinct m)
//    from Movie m
//    join m.director d
//    where d.firstName = :firstName
//      and d.lastName = :lastName
//""")
//    long countByDirector_FirstNameAndDirector_LastName(
//        @Param("firstName") String firstName,
//        @Param("lastName") String lastName);
//
//    @Query("""
//            select count(distinct m)
//            from Movie m
//            join m.actor a
//            where a.firstName = :firstName
//            and a.lastName = :lastName
//            """)
//    long countByActor_FirstNameAndActor_LastName(
//        @Param("firstName") String firstName,
//        @Param("lastName") String lastName);


}
