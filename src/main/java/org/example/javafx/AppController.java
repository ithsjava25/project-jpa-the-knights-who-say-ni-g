package org.example.javafx;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.service.CustomerService;
import org.example.service.MovieService;
import org.example.service.RentalService;
import org.example.tables.Movie;

import java.util.List;

public class AppController {

    @Inject
    CustomerService customerService;
    @Inject
    MovieService movieService;
    @Inject
    RentalService rentalService;
    @Inject
    private Instance<Object> instance; //container injection

    @FXML
    BorderPane root;
    @FXML
    ScrollPane scrollTopPane;
    @FXML
    ScrollPane scrollLowPane;
    @FXML
    HBox topMoviecard;
    @FXML
    HBox lowMoviecard;


    private final AppModel model = new AppModel();

    @FXML
    public void initialize() {
        getAllMovies();
        //printOutButtons();
    }

    public Node createMoviePosters(Movie movie) {
        System.out.println("I got a movie!" + movie.getTitle());
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setSpacing(5);

        Rectangle poster = new Rectangle(120, 160);
        poster.setArcHeight(15);
        poster.setArcWidth(15);
        poster.setFill(Color.WHITE);
        poster.setStroke(Color.BLACK);

        Label titleLabel = new Label(movie.getTitle());
        card.getChildren().addAll(poster, titleLabel);
        topMoviecard.getChildren().add(card);

        card.setOnMouseClicked(event -> handleMovieClick(movie));

        return card;
    }


    private void handleMovieClick(Movie movie) {
        try {
            //load new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/movieposter.fxml"));

            loader.setControllerFactory(type -> instance.select(type).get());

            Parent nextView = loader.load();

            movieposterController controller = loader.getController();

            controller.initData(movie, model);
            root.setCenter(nextView);
        } catch (Exception e) {
            System.out.println("Error from switching view!");
            e.printStackTrace();

            if (e.getCause() != null) {
                System.err.println("------Cause------");
                e.getCause().printStackTrace();
            }
        }
    }

    public void getAllMovies(){
        List<Movie> allMovies = movieService.getAllMovies();

        for(Movie movie : allMovies){
            Node moviePoster = createMoviePosters(movie);
        }
    }
}

