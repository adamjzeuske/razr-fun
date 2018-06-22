package com.razr.adam.shapes.repositories;

import com.razr.adam.shapes.entities.Circle;
import org.springframework.data.repository.CrudRepository;

public interface CircleRepository extends CrudRepository<Circle, Long> {
    
}
