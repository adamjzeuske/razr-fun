package com.razr.adam.shapes.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Square implements Shape {

    @Id
    @GeneratedValue
    private Long id;
    private double sideLength = 1; // The unit square?
    
    public Square() {}
    
    public Square(double initialSideLength) {
        if (initialSideLength > 0) {
            this.sideLength = initialSideLength;
        }
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the sideLength
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * @param sideLength the sideLength to set
     */
    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public double getArea() {
        return Math.pow(getSideLength(), 2.0);
    }
    
    @Override 
    public String toString() {
        return String.format("Square: Size = %f, Area = %f", getSideLength(), getArea());
    }
}
