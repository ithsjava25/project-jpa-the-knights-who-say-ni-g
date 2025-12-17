package org.example.repository;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Repository;
import org.example.tables.Customer;
import org.example.tables.Rental;

import java.util.List;

@Repository
public interface RentalRepository extends CrudRepository<Rental,Long> {

    @Find
    List<Rental> findByCustomerCustomerId(Long customerId);
    List<Rental> findByCustomer(Customer customer); // ? Search by a customer object

    // Find totalRentPrice?
    // Find returnDate?

    @Insert
    Rental createRental(Rental rental);
}
