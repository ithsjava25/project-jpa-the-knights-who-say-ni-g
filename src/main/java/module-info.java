module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires javafx.graphics;
    requires java.desktop;
    requires jakarta.persistence;
    requires io.github.classgraph;
    requires org.hibernate.orm.core;
    requires jakarta.data;
    requires jakarta.transaction;
    requires jakarta.cdi;

    opens org.example.javafx to javafx.fxml;

    opens org.example.tables to org.hibernate.orm.core;
    exports org.example.javafx;
}
