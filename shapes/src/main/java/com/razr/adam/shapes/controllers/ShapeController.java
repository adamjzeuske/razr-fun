package com.razr.adam.shapes.controllers;

import com.razr.adam.shapes.entities.Circle;
import com.razr.adam.shapes.entities.Shape;
import com.razr.adam.shapes.entities.Square;
import com.razr.adam.shapes.logic.ShapeBo;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShapeController {
    @Autowired 
    private ShapeBo _businessLogic;
    
    @RequestMapping(value="/shapes/circle", method=RequestMethod.POST)
    public ResponseEntity<Circle> saveCircle(@RequestBody Circle circle) {
        try
        {
            Circle newCircle = (Circle) _businessLogic.create(circle);
            return new ResponseEntity<Circle>(newCircle, HttpStatus.CREATED);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/shapes/circle/{id}", method=RequestMethod.GET)
    public ResponseEntity<Circle> getCircle(@PathVariable long id) {
        try
        {
            Circle newCircle = (Circle) _businessLogic.get(id, Circle.class.getTypeName());
            return new ResponseEntity<Circle>(newCircle, HttpStatus.OK);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/shapes/circle", method=RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Boolean> deleteCircle(@RequestBody Circle circle) {
        try
        {
            boolean deleted = _businessLogic.delete(circle);
            return Collections.singletonMap("success", deleted);
        }
        catch(Exception ex) {
            return Collections.singletonMap("success", false);
        }
    }

    @RequestMapping(value="/shapes/square", method=RequestMethod.POST)
    public ResponseEntity<Square> saveSquare(@RequestBody Square square) {
        try
        {
            Square newSquare = (Square) _businessLogic.create(square);
            return new ResponseEntity<Square>(newSquare, HttpStatus.CREATED);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/shapes/square/{id}", method=RequestMethod.GET)
    public ResponseEntity<Square> getSquare(@PathVariable long id) {
        try
        {
            Square newSquare = (Square) _businessLogic.get(id, Square.class.getTypeName());
            return new ResponseEntity<Square>(newSquare, HttpStatus.OK);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/shapes/square/{id}", method=RequestMethod.DELETE)
    @ResponseBody
    public Map<String, Boolean> deleteSquare(@PathVariable long id) {
        try
        {
            Square square = (Square)_businessLogic.get(id, Square.class.getTypeName());
            boolean deleted = _businessLogic.delete(square);
            return Collections.singletonMap("success", deleted);
        }
        catch(Exception ex) {
            return Collections.singletonMap("success", false);
        }
    }
    
    @RequestMapping(value="/shapes/bundle", method=RequestMethod.GET)
    public ResponseEntity<List<Shape>> getBundle() {
        try
        {
            // TODO: bake persistence into this...
            List<Shape> bundle = _businessLogic.generateBundle();
            return new ResponseEntity<List<Shape>>(bundle, HttpStatus.OK);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/shapes/sortedbundle", method=RequestMethod.GET)
    public ResponseEntity<List<Shape>> getSortedBundle() {
        try
        {
            // TODO: bake persistence into this...
            List<Shape> bundle = _businessLogic.generateBundle();
            List<Shape> sortedBundle = _businessLogic.sortByArea(bundle);
            return new ResponseEntity<List<Shape>>(sortedBundle, HttpStatus.OK);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/shapes/meanorientedbundle", method=RequestMethod.GET)
    public ResponseEntity<List<Shape>> getMeanOrientedBundle() {
        try
        {
            // TODO: bake persistence into this...
            List<Shape> bundle = _businessLogic.generateBundle();
            List<Shape> orientedBundle = _businessLogic.findClosestShapesToAverageArea(bundle);
            return new ResponseEntity<List<Shape>>(orientedBundle, HttpStatus.OK);
        }
        catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
