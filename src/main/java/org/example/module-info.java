module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires javafx.graphics;
    requires java.desktop;

    opens org.example.javafx to javafx.fxml;
    exports org.example.javafx;
}
