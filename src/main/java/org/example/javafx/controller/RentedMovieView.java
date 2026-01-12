package org.example.javafx.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
//Skapad för att kunna visa kolumner från två tabeller i tableview
public class RentedMovieView {
    private final Long rentalId;
    private final SimpleStringProperty title;
    private final SimpleObjectProperty<BigDecimal> price;
    private final SimpleObjectProperty<LocalDateTime> returnDate;

    public RentedMovieView(Long rentalId, String title, BigDecimal price, LocalDateTime returnDate) {
        this.rentalId = rentalId;
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleObjectProperty<>(price);
        this.returnDate = new SimpleObjectProperty<>(returnDate);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public String getTitle() {
        return title.get();
    }

    public LocalDateTime getReturnDate() {
        return returnDate.get();
    }

    public Long getRentalId() {
        return rentalId;
    }
}
