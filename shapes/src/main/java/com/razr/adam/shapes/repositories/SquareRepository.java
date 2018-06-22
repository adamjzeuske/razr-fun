package com.razr.adam.shapes.repositories;

import com.razr.adam.shapes.entities.Square;
import org.springframework.data.repository.CrudRepository;

public interface SquareRepository extends CrudRepository<Square, Long> {
    
}
