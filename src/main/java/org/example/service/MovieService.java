package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.javafx.HibernateUtil;
import org.example.repository.MovieRepository;
import org.example.repository.MovieRepository_;
import org.example.tables.Movie;

import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class MovieService{
    //private final MovieRepository movieRepository;

    MovieRepository movieRepository = new MovieRepository_(HibernateUtil.getSessionFactory().openStatelessSession());


    public MovieService() {
    }

    public Optional<Movie> findMovieById(int id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll().toList();
    }

    public Optional<Movie> findMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }
//
//    public List<Movie> findMoviesByGenre(String genre) {
//        return movieRepository.findByGenre(genre);
//    }
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
