package org.example.repository;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Repository;
import org.example.tables.Customer;

import java.util.Optional;

@Repository

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Find
    Optional<Customer> findByEmail(String email);
}
