package com.razr.adam.shapes.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Circle implements Shape {
    @Id
    @GeneratedValue
    private Long Id;
    private double radius = 1;  // By default the unit circle!!  What could be better? 

    public Circle() {}
    
    public Circle(double initialRadius) {
        if (initialRadius > 0) {
            this.radius = initialRadius;
        }
    }
    
    /** 
     * @return the Id
     */
    public Long getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(Long Id) {
        this.Id = Id;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        double sideSquared = Math.pow(getRadius(), 2.0);
        return sideSquared * Math.PI;
    }
    
    @Override 
    public String toString() {
        return String.format("Circle: Radius = %f, Area = %f", getRadius(), getArea());
    }
}
