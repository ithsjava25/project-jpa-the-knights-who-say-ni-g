package org.example.repository;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.tables.RentalMovie;

@Repository
public interface RentalMovieRepository extends CrudRepository<RentalMovie, Long> {
}
