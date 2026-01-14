package org.example.javafx.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
//Skapad för att kunna visa kolumner från två tabeller i tableview
public class RentedMovieView {
    private final Long rentalMovieId;
    private final SimpleStringProperty title;
    private final SimpleObjectProperty<BigDecimal> price;
    private final SimpleObjectProperty<LocalDateTime> returnDate;
    private final SimpleObjectProperty<BigDecimal> additionalCost;




    public RentedMovieView(Long rentalMovieId, String title, BigDecimal price, LocalDateTime returnDate, BigDecimal additionalCost) {
        this.rentalMovieId = rentalMovieId;
        this.title = new SimpleStringProperty(title);
        this.price = new SimpleObjectProperty<>(price);
        this.returnDate = new SimpleObjectProperty<>(returnDate);
        this.additionalCost = new SimpleObjectProperty<>(additionalCost);
    }

    public BigDecimal getAdditionalCost() {
        return additionalCost.get();
    }

    public BigDecimal getPrice() {
        BigDecimal additional = additionalCost.get();
        return additional != null ? price.get().add(additional) : price.get();
    }

    public String getTitle() {
        return title.get();
    }

    public LocalDateTime getReturnDate() {
        return returnDate.get();
    }

    public Long getRentalId() {
        return rentalMovieId;
    }
}
