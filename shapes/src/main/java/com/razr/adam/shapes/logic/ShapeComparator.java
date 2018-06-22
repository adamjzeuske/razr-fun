package com.razr.adam.shapes.logic;

import com.razr.adam.shapes.entities.Shape;
import java.util.Comparator;

public class ShapeComparator implements Comparator<Shape> {

    private boolean _descending = false;
    
    public ShapeComparator(boolean descending) {
        this._descending = descending;
    }
    
    @Override
    public int compare(Shape o1, Shape o2) {
        if (o1.getArea() - o2.getArea() > 0) {
            return _descending ? 1 : -1;
        } else if (o1.getArea() == o2.getArea()) {
            return 0;
        } else if (o1.getArea() - o2.getArea() < 0) {
            return _descending ? -1 : 1;
        }
        return 0;
    }
}
