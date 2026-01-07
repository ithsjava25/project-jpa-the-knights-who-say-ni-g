module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires javafx.graphics;
    requires java.desktop;
    requires jakarta.persistence;
    requires io.github.classgraph;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.data;
    requires jakarta.transaction;
    requires jakarta.cdi;
    requires jakarta.inject;
    requires jakarta.el;
    requires weld.se.core;
    requires narayana.jta;

    opens org.example.javafx;
    opens org.example.service;
    opens org.example.tables;
    opens org.example.repository;

    exports org.example.javafx;
}
