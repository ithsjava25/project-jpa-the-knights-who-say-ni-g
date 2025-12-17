package org.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class AppController {

    @FXML
    BorderPane root;
    @FXML
    Button searchmovietitle;
    @FXML
    Button placeorder;

    private final AppModel model = new AppModel();
}
