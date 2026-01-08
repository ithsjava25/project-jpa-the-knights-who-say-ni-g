package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.javafx.HibernateUtil;
import org.example.repository.MovieRepository;
import org.example.repository.MovieRepository_;
import org.example.tables.Movie;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class MovieService{


    StatelessSession ss = HibernateUtil.getSessionFactory().openStatelessSession();
    private final MovieRepository movieRepository = new MovieRepository_(ss);


    public Optional<Movie> findMovieById(int id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAllMovies(){
        Transaction tx = ss.beginTransaction();
      try {
          List<Movie> movies = movieRepository.findAll().toList();
          tx.commit();
          return movies;
      } catch (Exception e) {
          tx.rollback();
          throw e;
      }
    }

    public Optional<Movie> findMovieByTitle(String title) {
        Transaction tx = ss.beginTransaction();
       try {
           Optional<Movie> movie = movieRepository.findByTitleIgnoringCase(title);
           tx.commit();
           return movie;
       } catch (Exception e) {
           tx.rollback();
           throw e;
       }
    }

    public List<Movie> findMoviesByGenre(String genre) {
        Transaction tx = ss.beginTransaction();
        try {
           List<Movie> movies = movieRepository.findByGenreIgnoringCase(genre);
           tx.commit();
           return movies;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

//
//    public List<Movie> findMoviesByDuration(int duration) {
//        return movieRepository.findByDuration(duration);
//    }
//
//    public List<Movie> findByPrice(BigDecimal price) {
//        return movieRepository.findByPrice(price);
//    }
//
//    public List<Movie> findByActor(String firstName, String lastName) {
//        return movieRepository.findByActor_FirstNameAndActor_LastName(firstName, lastName);
//    }
//
//    public long countMoviesByGenre(String genre) {
//        return movieRepository.countByGenre(genre);
//    }
//
//    public long countMoviesByDuration(int duration) {
//        return movieRepository.countByDuration(duration);
//    }
//
//
//    public long countMoviesByActor(String firstName, String lastName) {
//        return movieRepository.countByActor_FirstNameAndActor_LastName(firstName, lastName);
//    }
//
//    public void saveMovie(Movie movie) {
//        movieRepository.save(movie);
//    }
//
//    @Transactional
//    public boolean deleteMovie(int id) {
//        if (movieRepository.existsById(id)){
//            movieRepository.deleteById(id);
//            return  true;
//        }
//        return false;
//    }
}
