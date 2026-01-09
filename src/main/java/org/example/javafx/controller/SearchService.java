package org.example.javafx.controller;

import jakarta.enterprise.context.ApplicationScoped;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@ApplicationScoped
public class SearchService {

    // The engine --> StringProperty
    private final StringProperty searchQuery = new SimpleStringProperty("");

    // Listener --> Makes it possible for other classes to "subscribe"
    public StringProperty searchQueryProperty() {
        return searchQuery;
    }

    // Writer--> Used by the search field in BlockBusterLabelController to send text
    public void setSearchQuery(String searchQuery) {
        this.searchQuery.set(searchQuery);
    }

    // Reader--> To se what is written
    public String getSearchQuery() {
        return searchQuery.get();
    }
}
