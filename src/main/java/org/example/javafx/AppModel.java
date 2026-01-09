package org.example.javafx;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.tables.Movie;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AppModel {
    @Inject
    MovieService movieService;
    @Inject
    CustomerService customerService;

    private List<Movie>movieList;
    private ObservableList<Movie> shoppingCartList = FXCollections.observableArrayList();

    public AppModel(){

    }
    @PostConstruct
    public void init(){
    }
    public void testStart(){
        movieList = movieService.getAllMovies();
    }

    public List<Movie>getMovieList(){
        return movieList;
    }
    public void addToShoppingCart(Movie movie){
        shoppingCartList.add(movie);
    }
    public ObservableList<Movie> getShoppingCartList(){
        return shoppingCartList;
    }
    public void removeFromShoppingCart(Movie movie){
        shoppingCartList.remove(movie);
    }

    public void clearShoppingCart(){
        shoppingCartList.clear();
    }






}
