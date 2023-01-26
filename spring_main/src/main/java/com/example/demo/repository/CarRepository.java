package com.example.demo.repository;

import com.example.demo.model.Car;
import org.springframework.data.repository.CrudRepository;

/**
 * Manage database operations for a car entity
 */
public interface CarRepository extends CrudRepository<Car, Integer> {

    public Car findByMarque(String marque);
    public Car findByPlaque(String plaque);

    void deleteByPlaque(String plaque);
}
