package com.razr.adam.shapes.logic;

import com.razr.adam.shapes.entities.Circle;
import com.razr.adam.shapes.entities.Shape;
import com.razr.adam.shapes.entities.Square;
import com.razr.adam.shapes.repositories.CircleRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShapeBoImplTest {

    @Mock
    CircleRepository _mockCircleRepo = Mockito.mock(CircleRepository.class);
    
    @InjectMocks
    ShapeBoImpl sut = new ShapeBoImpl();
    
    @Test
    public void delete_aShape_callsRepo() {
        Circle circle = new Circle(1);
        sut.delete(circle);
        
        verify(_mockCircleRepo, times(1)).delete(circle);
    }
    
    @Test
    public void sortByArea_shapes_ordersDescending() {
        List<Shape> shapes = new ArrayList<Shape>();
        shapes.add(new Circle(1));
        shapes.add(new Square(1));
        
        List<Shape> sortedShapes = sut.sortByArea(shapes);

        assertEquals(1, sortedShapes.get(0).getArea(), 0);
    }
    
    @Test
    public void findClosestShapesToAverageArea_shapes_returnsClosest() {
        List<Shape> shapes = new ArrayList<Shape>();
        shapes.add(new Square(1));
        shapes.add(new Square(2));
        shapes.add(new Square(3));
        shapes.add(new Square(4));
        shapes.add(new Square(5));
        
        List<Shape> closestShapes = sut.findClosestShapesToAverageArea(shapes);
        
        // Average should be 11, so closest area would be 9...
        assertEquals(closestShapes.size(), 1);
        assertEquals(closestShapes.get(0).getArea(), 9.0, 0);
    }
    
    @Test
    public void generateBundle_noInput_returns100Shapes() {
        List<Shape> shapes = sut.generateBundle();
        assertEquals(shapes.size(), 100);
    }
}
