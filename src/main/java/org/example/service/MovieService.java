package org.example.service;

import jakarta.transaction.Transactional;
import org.example.repository.MovieRepository;
import org.example.tables.Movie;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> findMovieById(int id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> findMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    public List<Movie> findMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> findMoviesByDuration(int duration) {
        return movieRepository.findByDuration(duration);
    }

    public List<Movie> findByPrice(BigDecimal price) {
        return movieRepository.findByPrice(price);
    }

    public List<Movie> findByDirector(String firstName, String lastName) {
        return movieRepository.findByDirector_FirstNameAndDirector_LastName(firstName, lastName);
    }

    public List<Movie> findByActor(String firstName, String lastName) {
        return movieRepository.findByActor_FirstNameAndActor_LastName(firstName, lastName);
    }

    public long countMoviesByGenre(String genre) {
        return movieRepository.countByGenre(genre);
    }

    public long countMoviesByDuration(int duration) {
        return movieRepository.countByDuration(duration);
    }

    public long countMoviesByDirector(String firstName, String lastName) {
        return movieRepository.countByDirector_FirstNameAndDirector_LastName(firstName, lastName);
    }

    public long countMoviesByActor(String firstName, String lastName) {
        return movieRepository.countByActor_FirstNameAndActor_LastName(firstName, lastName);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Transactional
    public boolean deleteMovie(int id) {
        if (movieRepository.existsById(id)){
            movieRepository.deleteById(id);
            return  true;
        }
        return false;
    }
}
