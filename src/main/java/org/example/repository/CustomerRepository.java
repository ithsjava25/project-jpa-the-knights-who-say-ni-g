package org.example.repository;
import jakarta.data.repository.CrudRepository;
import org.example.tables.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
}
