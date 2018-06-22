package com.razr.adam.shapes.logic;

import com.razr.adam.shapes.entities.Circle;
import com.razr.adam.shapes.entities.Shape;
import com.razr.adam.shapes.entities.Square;
import com.razr.adam.shapes.repositories.CircleRepository;
import com.razr.adam.shapes.repositories.SquareRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShapeBoImpl implements ShapeBo {
    
    @Autowired
    private CircleRepository _circleRepository;
    @Autowired
    private SquareRepository _squareRepository;
    
    @Override
    public Shape create(Shape shape) {
        Shape ret = null;
        if (shape instanceof Circle) {
            Circle theCircle = (Circle)shape;
            Circle newCircle = _circleRepository.save(theCircle);
            ret = newCircle;
        } else if (shape instanceof Square) {
            Square theSquare = (Square)shape;
            Square newSquare = _squareRepository.save(theSquare);
            ret = newSquare;
        }
        return ret;
    }

    @Override
    public Shape get(Long id, String typeName) {
        Shape ret = null;
        if (typeName.equals(Circle.class.getTypeName())) {
            Optional<Circle> theCircle = _circleRepository.findById(id);
            if (theCircle.isPresent()) {
                ret = (Shape)theCircle.get();
            }
        } else if (typeName.equals(Square.class.getTypeName())) {
            Optional<Square> theSquare = _squareRepository.findById(id);
            if (theSquare.isPresent()) {
                ret = (Shape)theSquare.get();
            }
        }
        return ret;
    }

    @Override
    public boolean delete(Shape shape) {
        boolean ret = false;
        if (shape instanceof Circle) {
            _circleRepository.delete((Circle)shape);
            ret = true;
        } else if (shape instanceof Square) {
            _squareRepository.delete((Square)shape);
            ret = true;
        }
        return ret;
    }

    @Override
    public List<Shape> sortByArea(List<Shape> shapes) {
        shapes.sort(new ShapeComparator(true));
        return shapes;
    }

    @Override
    public List<Shape> generateBundle() {
        List<Shape> ret = new ArrayList<Shape>();
        Random random = new Random();
        
        for (int i = 0; i < 50; i++) {
            // Random real number from 1 - 100...
            Square square = new Square(random.nextDouble() * 100 + 1);
            
            // Random real number between 2 and 100, so that radius is 1 - 50
            double diameter = (random.nextDouble() * 99 + 1) / 2;
            Circle circle = new Circle(diameter);
            ret.add(circle);
            ret.add(square);
        }
        return ret;
    }

    @Override
    public List<Shape> findClosestShapesToAverageArea(List<Shape> shapes) {
        List<Shape> ret = new ArrayList<Shape>();
        
        // First, get the average...
        double average, sum = 0.0;
        List<Double> areas = shapes.stream().map(s -> s.getArea()).collect(Collectors.toList());
        for (int i = 0; i < areas.size(); i++) {
            sum += areas.get(i);
        }
        average = sum / areas.size();
        
        // Now find the closest ones...
        for (int j = 0; j < shapes.size(); j++) {
            double theShapesArea = shapes.get(j).getArea();
            if (ret.isEmpty()) {
                // If we have no candidates, just add it...
                ret.add(shapes.get(j));
            } else {
                // If so, compare the current shape with the current reigning champion...
                if (Math.abs(average - theShapesArea) < Math.abs(average - ret.get(0).getArea())) {
                    // If we are less, clear the array and add the new champion...
                    ret.clear();
                    ret.add(shapes.get(j));
                } else if (Math.abs(average - theShapesArea) == Math.abs(average - ret.get(0).getArea())) {
                    // We _could_ be equal, so add it to the list...
                    ret.add(shapes.get(j));
                }
            }
        }
        
        return ret;
    }
}
