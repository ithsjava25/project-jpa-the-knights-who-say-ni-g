package org.example.repository;

import jakarta.data.repository.*;
import org.example.tables.Rental;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface RentalRepository extends CrudRepository<Rental,Long> {

//    @Find
//    List<Rental> findByCustomerCustomerId(Long customerId);

    // Find totalRentPrice?
    // Find returnDate?

    @Insert
    Rental createRental(Rental rental);

    //deleteByRental i movierental
    @Find
    List<Rental> findByRentalDateLessThan(LocalDateTime rentalDate);

    @Delete
    void delete(Rental rental);
}
