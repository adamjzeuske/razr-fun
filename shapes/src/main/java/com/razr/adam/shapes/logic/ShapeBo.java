package com.razr.adam.shapes.logic;

import com.razr.adam.shapes.entities.Shape;
import java.util.List;

public interface ShapeBo {
    Shape get(Long id, String typeName);
    Shape create(Shape shape);
    boolean delete(Shape shape);
    
    List<Shape> sortByArea(List<Shape> shapes);
    List<Shape> generateBundle();
    List<Shape> findClosestShapesToAverageArea(List<Shape> shapes);
}
