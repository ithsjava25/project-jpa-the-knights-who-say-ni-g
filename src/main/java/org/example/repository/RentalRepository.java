package org.example.repository;

import jakarta.data.repository.*;
import org.example.tables.Rental;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface RentalRepository extends CrudRepository<Rental,Long> {
    //deleteByRental i movierental
    @Find
    List<Rental> findByRentalDateLessThan(LocalDateTime rentalDate);

}
