package org.example.repository;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.tables.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
}
