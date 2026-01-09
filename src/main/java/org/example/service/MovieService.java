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
          if (tx != null) tx.rollback();
          throw e;
      }
    }

    public List<Movie> searchMovies(String query) {
        // If Search field is empty, show all movies as default
        if (query == null || query.isEmpty()) {
            return getAllMovies();
        }

        Transaction tx = ss.beginTransaction();
       try {
          String searchParam = "%" + query + "%";

          List<Movie> movies = movieRepository.smartSearch(searchParam);

           tx.commit();
           return movies;
       } catch (Exception e) {
           if (tx != null) tx.rollback();
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
            if (tx != null) tx.rollback();
            throw e;
        }
    }


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
